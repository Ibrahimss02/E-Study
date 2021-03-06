package com.drunken.e_study.ui.welcomeScreens.userLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.dao.UserDatabaseDao

class UserLoginViewModelFactory(private val database : UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserLoginViewModel::class.java)){
            return UserLoginViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}