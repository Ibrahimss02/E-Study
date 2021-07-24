package com.drunken.e_study.ui.welcomeScreens.userLogin

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dto.User
import com.drunken.e_study.dao.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLoginViewModel(private val database: UserDatabaseDao) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    private val _showProgressDialog = MutableLiveData<Boolean?>()
    val showProgressDialog: LiveData<Boolean?>
        get() = _showProgressDialog

    private val _showErrorSnackbar = MutableLiveData<Pair<Boolean, String>>()
    val showErrorSnackbar: LiveData<Pair<Boolean, String>>
        get() = _showErrorSnackbar

    private val _loginSuccess = MutableLiveData(false)
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun onClickSignIn() {
        _showProgressDialog.value = true
        viewModelScope.launch {
            val email = email.value!!.trim { it <= ' ' }
            val password = password.value!!

            if (validateForm(password = password, email = email)) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModelScope.launch(Dispatchers.IO) {
                            insertNewUserToDb()
                        }
                    } else {
                        _showErrorSnackbar.value = Pair(true, it.exception!!.message!!)
                        _showProgressDialog.value = false
                    }
                }
            }
            _showProgressDialog.value = false
        }
    }

    private suspend fun insertNewUserToDb() {
        firestore.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            if (user != null) {
                viewModelScope.launch {
                    database.insert(user)
                    _loginSuccess.value = true
                    Log.i("Login", "$user inserted")
                }
            }
        }.addOnFailureListener {
            _showErrorSnackbar.value = Pair(true, it.message!!)
            _showProgressDialog.value = false
        }
    }

    private fun validateForm(password: String, email: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                _showErrorSnackbar.value = Pair(true, "Please enter an email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                _showErrorSnackbar.value = Pair(true, "Please enter a password")
                false
            }
            else -> true
        }
    }

    fun doneNavigating() {
        _loginSuccess.value = false
        _showProgressDialog.value = null
    }
}