package com.drunken.e_study.mainScreens.home

import androidx.lifecycle.*
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val database : UserDatabaseDao) : ViewModel() {

    private val _user = MediatorLiveData<User>()
    val user : LiveData<User>
    get() = _user

    companion object {
        const val SD_PATH = "SD_courses"
        const val SMP_PATH = "SMP_courses"
        const val SMA_PATH = "SMA_courses"
        const val KULIAH_PATH = "Kuliah_courses"
    }

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.Main) {
            _user.value = database.getLastCurrentUser()
        }
    }

    private val _args = MutableLiveData<List<String>>()
    val args : LiveData<List<String>>
    get() = _args

    fun navigateToBrowse(category : String){
        when(category){
            SD_PATH -> {
                _args.value = listOf(SD_PATH, "Kursus SD")
            }
            SMP_PATH -> {
                _args.value = listOf(SMP_PATH, "Kursus SMP")
            }
            SMA_PATH -> {
                _args.value = listOf(SMA_PATH, "Kursus SMA")
            }
            KULIAH_PATH -> {
                _args.value = listOf(KULIAH_PATH, "Kursus Kuliah")
            }
        }
    }
}