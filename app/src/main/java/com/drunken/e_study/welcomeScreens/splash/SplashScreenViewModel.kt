package com.drunken.e_study.welcomeScreens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome : LiveData<Boolean>
    get() = _navigateToHome
    private val auth = Firebase.auth

    init {
        viewModelScope.launch {
            delay(2000)
            _navigateToHome.value = auth.currentUser != null
        }
    }

    fun doneNavigating(){
        _navigateToHome.value = null
    }
}