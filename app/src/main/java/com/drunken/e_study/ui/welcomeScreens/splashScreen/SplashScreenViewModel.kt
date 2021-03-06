package com.drunken.e_study.ui.welcomeScreens.splashScreen

import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.dto.User
import com.drunken.e_study.dao.UserDatabaseDao
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val database: UserDatabaseDao) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private val firestore = Firebase.firestore

    init {
        viewModelScope.launch {
            val user = database.lastCurrentUser()
            updateUserData(user)
        }
    }

    private fun updateUserData(user: User?) {
        if (user != null) {
            firestore.collection("users").document(user.id).get().addOnSuccessListener {
                val userNew = it.toObject(User::class.java)
                Log.i("splash fire", userNew.toString())
                userNew?.let { user ->
                    viewModelScope.launch {
                        _user.value = user
                        database.update(user)
                    }
                }
            }
        } else {
            _user.value = null
            Log.i("splash fire 1", _user.value.toString())
        }
    }
}