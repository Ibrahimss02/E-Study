package com.drunken.e_study.ui.welcomeScreens.userRegister

import android.text.TextUtils
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRegisterViewModel(private val userDatabase : UserDatabaseDao) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _showErrorSnackbar = MutableLiveData<Pair<Boolean, String>?>()
    val showErrorSnackbar: MutableLiveData<Pair<Boolean, String>?>
        get() = _showErrorSnackbar

    private val _showProgressDialog = MutableLiveData<Pair<Boolean, String>?>()
    val showProgressDialog: LiveData<Pair<Boolean, String>?>
        get() = _showProgressDialog

    val email = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

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
                        viewModelScope.launch(Dispatchers.IO) {
                            val firebaseUser = it.result!!.user!!
                            val user =
                                User(firebaseUser.uid, username, email)
                            registerUserToFirestore(user, firebaseUser.uid)
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

    private suspend fun registerUserToFirestore(userInfo: User, userUid: String) {
        withContext(Dispatchers.Default) {
            firestore.collection("users").document(userUid).set(userInfo, SetOptions.merge())
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