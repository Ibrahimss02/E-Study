package com.drunken.e_study.ui.welcomeScreens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.UserDatabaseDao
import java.lang.IllegalArgumentException

class SplashScreenViewModelFactory(private val database : UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)){
            return SplashScreenViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}