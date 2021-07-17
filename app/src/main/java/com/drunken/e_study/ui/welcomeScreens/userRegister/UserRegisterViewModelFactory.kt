package com.drunken.e_study.ui.welcomeScreens.userRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.UserDatabaseDao

class UserRegisterViewModelFactory(private val database : UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserRegisterViewModel::class.java)){
            return UserRegisterViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}