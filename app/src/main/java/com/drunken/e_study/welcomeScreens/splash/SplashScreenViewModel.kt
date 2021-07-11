package com.drunken.e_study.welcomeScreens.splash

import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val database : UserDatabaseDao) : ViewModel() {

    private val _user = MediatorLiveData<User>()
    val user : LiveData<User>
    get() = _user

    init {
        viewModelScope.launch(Dispatchers.Main) {
            _user.value = database.getLastCurrentUser()
            Log.i("login awal", _user.value.toString())
        }
    }
}