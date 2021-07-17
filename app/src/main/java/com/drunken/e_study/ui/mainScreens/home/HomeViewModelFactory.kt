package com.drunken.e_study.ui.mainScreens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drunken.e_study.database.CourseDatabaseDao
import com.drunken.e_study.database.UserDatabaseDao

class HomeViewModelFactory(private val database : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(database, courseDatabase) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}