package com.drunken.e_study.ui.mainScreens.courseDetailShop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.dao.UserDatabaseDao

class CourseDetailShopViewModelFactory(private val args : String, private val userDb : UserDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseDetailShopViewModel::class.java)){
            return CourseDetailShopViewModel(args, userDb) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}