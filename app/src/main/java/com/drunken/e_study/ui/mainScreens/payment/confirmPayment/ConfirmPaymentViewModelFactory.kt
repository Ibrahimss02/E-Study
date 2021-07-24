package com.drunken.e_study.ui.mainScreens.payment.confirmPayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dao.UserDatabaseDao

class ConfirmPaymentViewModelFactory(private val userDb : UserDatabaseDao, private val courseDb : CourseDatabaseDao, private val arguments : String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmPaymentViewModel::class.java)){
            return ConfirmPaymentViewModel(userDb, courseDb, arguments) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}