package com.drunken.e_study.ui.mainScreens.cart

import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.database.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val userDatabase: UserDatabaseDao,
    private val courseDatabase: CourseDatabaseDao
) : ViewModel() {

    private val _course = MutableLiveData<ArrayList<Course>>()
    val course: LiveData<ArrayList<Course>>
        get() = _course

    private val _navigateToCourseDetail = MutableLiveData<String?>()
    val navigateToCourseDetail: LiveData<String?>
        get() = _navigateToCourseDetail

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _proceedToPayment = MutableLiveData<Boolean?>()
    val proceedToPayment : LiveData<Boolean?>
    get() = _proceedToPayment

    init {
        viewModelScope.launch {
            _user.value = userDatabase.lastCurrentUser()
            val listTemp = _user.value!!.courseOnCart
            if (!(listTemp.isNullOrEmpty())) {
                getUserCourse()
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

    fun onCourseItemClicked(courseId: String) {
        _navigateToCourseDetail.value = courseId
    }

    fun onCourseNavigated() {
        _navigateToCourseDetail.value = null
    }

    fun refreshCourse() {
        viewModelScope.launch {
            userDatabase.update(_user.value!!)
        }
    }

    fun onProceedClicked(){
        _proceedToPayment.value = true
    }

    fun onProceedNavigated(){
        _proceedToPayment.value = null
    }

}