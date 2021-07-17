package com.drunken.e_study.ui.mainScreens.account

import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.database.Course
import com.drunken.e_study.database.CourseDatabaseDao
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(
    private val userDatabase: UserDatabaseDao,
    private val courseDatabase: CourseDatabaseDao
) : ViewModel() {

    companion object {
        private const val SD_PATH = "SD_courses"
        private const val SMP_PATH = "SMP_courses"
    }

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _course = MutableLiveData<ArrayList<Course>>()
    val course: LiveData<ArrayList<Course>>
        get() = _course

    private val _notifyEmptyList = MutableLiveData<Boolean?>()
    val notifyEmptyList: LiveData<Boolean?>
        get() = _notifyEmptyList

    private val _signOut = MutableLiveData(false)
    val signOut: LiveData<Boolean>
        get() = _signOut

    init {
        viewModelScope.launch {
            _user.value = userDatabase.lastCurrentUser()
            val listTemp = _user.value!!.coursesId
            if (!(listTemp.isNullOrEmpty())) {
                getUserCourse()
            } else {
                _notifyEmptyList.value = true
            }
        }
    }

    suspend fun getUserCourse() {
        Log.i("cart", "called user")
        val coursesIdList = arrayListOf<String>()
        val courses = arrayListOf<Course>()
        Log.i("cart", "terpanggil dengan ${_user.value!!.courseOnCart.toString()}")
        _user.value?.courseOnCart?.forEach {
            coursesIdList.add(it)
        }
        coursesIdList.forEach {
            val course = courseDatabase.getCourse(it)
            courses.add(course)
        }
        _course.value = courses
        Log.i("account", _course.value.toString())
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.Main) {
            auth.signOut()
            userDatabase.clearAllUser()
            _signOut.value = true
        }
    }

    fun doneSigningOut() {
        _signOut.value = false
    }

    fun refreshUser() {
        viewModelScope.launch {
            userDatabase.update(_user.value!!)
        }
    }

    fun doneNotifyingEmpty() {
        _notifyEmptyList.value = null
    }
}