package com.drunken.e_study.ui.mainScreens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.CourseDatabaseDao
import com.drunken.e_study.database.UserDatabaseDao

class AccountViewModelFactory(private val userDatabase : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)){
            return AccountViewModel(userDatabase, courseDatabase) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}