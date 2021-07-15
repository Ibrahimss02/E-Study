package com.drunken.e_study.mainScreens.home

import androidx.lifecycle.*
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
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
        viewModelScope.launch {
            _user.value = database.lastCurrentUser()
        }
    }

    private val _args = MutableLiveData<Pair<String, String>?>()
    val args : LiveData<Pair<String, String>?>
    get() = _args

    fun navigateToBrowse(category : String){
        when(category){
            SD_PATH -> {
                _args.value = Pair(SD_PATH, "Kursus SD")
            }
            SMP_PATH -> {
                _args.value = Pair(SMP_PATH, "Kursus SMP")
            }
            SMA_PATH -> {
                _args.value = Pair(SMA_PATH, "Kursus SMA")
            }
            KULIAH_PATH -> {
                _args.value = Pair(KULIAH_PATH, "Kursus Kuliah")
            }
        }
    }

    fun doneNavigating(){
        _args.value = null
    }
}