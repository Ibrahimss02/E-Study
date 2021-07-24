package com.drunken.e_study.ui.mainScreens.cart

import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dao.UserDatabaseDao
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dto.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CartViewModel(
    private val userDatabase: UserDatabaseDao,
    private val courseDatabase: CourseDatabaseDao
) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _course = MutableLiveData<ArrayList<Course>>()
    val course: LiveData<ArrayList<Course>>
        get() = _course

    private val _navigateToCourseDetail = MutableLiveData<String?>()
    val navigateToCourseDetail: LiveData<String?>
        get() = _navigateToCourseDetail

    private val _user = MutableLiveData<User>()

    private val _proceedToPayment = MutableLiveData<Boolean?>()
    val proceedToPayment : LiveData<Boolean?>
    get() = _proceedToPayment

    private val _notifyItemRemoved = MutableLiveData<String?>()
    val notifyItemRemoved : LiveData<String?>
    get() = _notifyItemRemoved

    init {
        viewModelScope.launch {
            _user.value = userDatabase.lastCurrentUser()
            val listTemp = _user.value!!.courseOnCart
            if (!(listTemp.isNullOrEmpty())) {
                getUserCourse()
            }
        }
    }

    private suspend fun getUserCourse() {
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

    fun onCourseDeleteAction(course: Course){
        viewModelScope.launch {
            _course.value!!.remove(course)
            _user.value!!.courseOnCart?.remove(course.id)
            userDatabase.update(_user.value!!)
            updateUserAtCloud()
            _notifyItemRemoved.value = course.title
        }
    }

    private fun updateUserAtCloud() {
        firestore.collection("users").document(_user.value!!.id).set(_user.value!!).addOnSuccessListener {
            Log.i("cart firestore", "user cart updated")
        }
    }

    fun onProceedClicked(){
        _proceedToPayment.value = true
    }

    fun onProceedNavigated(){
        _proceedToPayment.value = null
    }

    fun notified() {
        _notifyItemRemoved.value = null
    }

}