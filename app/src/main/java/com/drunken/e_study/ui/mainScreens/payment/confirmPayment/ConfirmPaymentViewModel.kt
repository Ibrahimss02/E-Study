package com.drunken.e_study.ui.mainScreens.payment.confirmPayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dto.User
import com.drunken.e_study.dao.UserDatabaseDao
import kotlinx.coroutines.launch

class ConfirmPaymentViewModel(private val userDatabase : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao, private val args: String?) : ViewModel() {

    private val _arguments = MutableLiveData<String?>()
    val arguments : LiveData<String?>
    get() = _arguments

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

    private val _navigateToMethodChooser = MutableLiveData<Boolean?>()
    val navigateToMethodChooser : LiveData<Boolean?>
    get() = _navigateToMethodChooser

    private val _navigateToPaymentInfo = MutableLiveData<Boolean?>()
    val navigateToPaymentInfo : LiveData<Boolean?>
    get() = _navigateToPaymentInfo


    init {
        viewModelScope.launch {
            _user.value = userDatabase.lastCurrentUser()
            val listTemp = _user.value!!.courseOnCart
            if (!(listTemp.isNullOrEmpty())) {
                getUserCourse()
            }
            _arguments.value = args
        }
    }

    private suspend fun getUserCourse() {
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

    fun onMethodChooseClick(){
        _navigateToMethodChooser.value = true
    }

    fun onMethodChooserNavigated() {
        _navigateToMethodChooser.value = null
    }

    fun onPayActionClick(){
        _navigateToPaymentInfo.value = true
    }

    fun onPaymentInfoNavigated(){
        _navigateToPaymentInfo.value = null
    }

}