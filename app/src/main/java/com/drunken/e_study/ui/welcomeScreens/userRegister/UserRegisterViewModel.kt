package com.drunken.e_study.ui.welcomeScreens.userRegister

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dto.User
import com.drunken.e_study.dao.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRegisterViewModel(private val userDatabase : UserDatabaseDao) : ViewModel() {

    private val firestore = Firebase.firestore
    private val cloudStorageRef = Firebase.storage.reference

    private val _showErrorSnackbar = MutableLiveData<Pair<Boolean, String>?>()
    val showErrorSnackbar: MutableLiveData<Pair<Boolean, String>?>
        get() = _showErrorSnackbar

    private val _showProgressDialog = MutableLiveData<Pair<Boolean, String>?>()
    val showProgressDialog: LiveData<Pair<Boolean, String>?>
        get() = _showProgressDialog

    private val _pickImage = MutableLiveData<Boolean?>()
    val pickImage : LiveData<Boolean?>
    get() = _pickImage

    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    val imageUriString = MutableLiveData<String>()

    private val _registerSuccess = MutableLiveData(false)
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

    private val auth = Firebase.auth

    init {
        viewModelScope.launch {
            userDatabase.clearAllUser()
        }
    }


    fun registerUser() {
        viewModelScope.launch {
            val email = email.value!!.trim { it <= ' ' }
            val username = username.value!!.trim { it <= ' ' }
            val password = password.value!!
            val confirmPassword = confirmPassword.value!!


            if (validateForm(email, password, confirmPassword, username)) {
                _showProgressDialog.value = Pair(true, "Validating your data")
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                            val firebaseUser = it.result!!.user!!
                            val user =
                                User(firebaseUser.uid, username, email)

                            if (!TextUtils.isEmpty(imageUriString.value)){
                                val imgUri = Uri.parse(imageUriString.value)
                                val imageRef = cloudStorageRef.child("images/" + firebaseUser.uid + "/" + imgUri.lastPathSegment)
                                val uploadTask = imageRef.putFile(imgUri)

                                uploadTask.addOnSuccessListener {
                                    imageRef.downloadUrl.addOnSuccessListener { url ->
                                        user.imageProfile = url.toString()
                                            registerUserToFirestore(user)
                                    }
                                }

                                uploadTask.addOnFailureListener { error ->
                                    Log.e("Register", error.message.toString())
                                }
                            } else {
                                registerUserToFirestore(user)
                            }

                    } else {
                        _showErrorSnackbar.value = Pair(true, it.exception!!.message!!)
                        _showProgressDialog.value = null
                    }
                }.addOnFailureListener {
                    _showErrorSnackbar.value = Pair(true, it.message!!)
                }
                _showProgressDialog.value = null
            }
        }
    }

    private fun registerUserToFirestore(userInfo: User) {
        viewModelScope.launch {
            firestore.collection("users").document(userInfo.id).set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    viewModelScope.launch {
                        userDatabase.insert(userInfo)
                        _registerSuccess.value = true
                    }
                }.addOnFailureListener {
                    _showErrorSnackbar.value = Pair(true, it.message!!)
                    _showProgressDialog.value = null
                }
        }
    }

    fun pickImage(){
        _pickImage.value = true
    }

    private fun validateForm(
        email: String,
        password: String,
        confirmPassword: String,
        username: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                _showErrorSnackbar.value = Pair(true, "Please input an email address")
                false
            }
            TextUtils.isEmpty(username) -> {
                _showErrorSnackbar.value = Pair(true, "Please enter a username")
                false
            }
            TextUtils.isEmpty(password) -> {
                _showErrorSnackbar.value = Pair(true, "Please enter a password")
                false
            }
            TextUtils.isEmpty(confirmPassword) -> {
                _showErrorSnackbar.value = Pair(true, "Please confirm your password")
                false
            }
            !TextUtils.equals(password, confirmPassword) -> {
                _showErrorSnackbar.value = Pair(true, "Password did not match")
                false
            }
            else -> true
        }
    }

    fun snackbarShowed() {
        _showErrorSnackbar.value = null
    }

    fun dismissProgressDialog() {
        _showProgressDialog.value = null
    }

    fun doneNavigating() {
        _registerSuccess.value = null
    }
}