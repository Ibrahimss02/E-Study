package com.drunken.e_study.mainScreens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.UserDatabaseDao

class AccountViewModelFactory(private val userDatabase : UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)){
            return AccountViewModel(userDatabase) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}