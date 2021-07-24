package com.drunken.e_study.ui.mainScreens.payment.processPayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.dao.UserDatabaseDao

class ProcessPaymentViewModelFactory(private val userDatabaseDao: UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProcessPaymentViewModel::class.java)){
            return ProcessPaymentViewModel(userDatabaseDao) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}