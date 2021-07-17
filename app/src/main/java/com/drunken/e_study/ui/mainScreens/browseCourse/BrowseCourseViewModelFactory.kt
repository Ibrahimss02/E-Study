package com.drunken.e_study.ui.mainScreens.browseCourse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BrowseCourseViewModelFactory(private val path : String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrowseCourseViewModel::class.java)){
            return BrowseCourseViewModel(path) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}