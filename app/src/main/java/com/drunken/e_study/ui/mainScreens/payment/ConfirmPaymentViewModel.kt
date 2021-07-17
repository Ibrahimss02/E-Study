package com.drunken.e_study.ui.mainScreens.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.database.Course
import com.drunken.e_study.database.CourseDatabaseDao
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
import kotlinx.coroutines.launch

class ConfirmPaymentViewModel(private val userDatabase : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao) : ViewModel() {

    private val _course = MutableLiveData<ArrayList<Course>>()
    val course: LiveData<ArrayList<Course>>
        get() = _course

    private val _navigateToCourseDetail = MutableLiveData<String?>()
    val navigateToCourseDetail: LiveData<String?>
        get() = _navigateToCourseDetail

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _totalPriceString = MutableLiveData("0")
    val totalPriceString : LiveData<String>
    get() = _totalPriceString


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
        val coursesIdList = arrayListOf<String>()
        val courses = arrayListOf<Course>()
        var totalPrice = 0L
        _user.value?.courseOnCart?.forEach {
            coursesIdList.add(it)
        }
        coursesIdList.forEach {
            val course = courseDatabase.getCourse(it)
            courses.add(course)
           totalPrice += course.price!!
        }
        _totalPriceString.value = "Rp %,d".format(totalPrice)
        _course.value = courses
    }

    fun onCourseItemClicked(courseId: String) {
        _navigateToCourseDetail.value = courseId
    }

    fun onCourseNavigated() {
        _navigateToCourseDetail.value = null
    }

}