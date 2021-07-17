package com.drunken.e_study.ui.mainScreens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.CourseDatabaseDao
import com.drunken.e_study.database.UserDatabaseDao

class ConfirmPaymentViewModelFactory(private val userDb : UserDatabaseDao, private val courseDb : CourseDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmPaymentViewModel::class.java)){
            return ConfirmPaymentViewModel(userDb, courseDb) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}