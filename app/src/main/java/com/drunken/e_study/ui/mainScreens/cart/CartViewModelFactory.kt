package com.drunken.e_study.ui.mainScreens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dao.UserDatabaseDao

class CartViewModelFactory(private val userDatabase : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(userDatabase, courseDatabase) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}