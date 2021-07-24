package com.drunken.e_study.ui.mainScreens.courseDetailShop

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dto.User
import com.drunken.e_study.dao.UserDatabaseDao
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CourseDetailShopViewModel(
    private val id : String = "", private val userDatabase: UserDatabaseDao
) : ViewModel(){

    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course>
    get() = _course

    private val _onCourseFetchingError = MutableLiveData(false)
    val onCourseFetchingError : LiveData<Boolean>
    get() = _onCourseFetchingError

    private val _notifyAddedToCart = MutableLiveData<Pair<Boolean, Boolean>?>()
    val notifyAddedToCart : LiveData<Pair<Boolean, Boolean>?>
    get() = _notifyAddedToCart

    private val db = Firebase.firestore

    private lateinit var user : User

    init {
        viewModelScope.launch {
            user = userDatabase.lastCurrentUser()
        }
        getCourseFromCloud(id)
    }

    private fun getCourseFromCloud(id: String) {
        db.collection("courses").whereEqualTo("id", id).addSnapshotListener { value, error ->
            if (error != null){
                Log.w("Course Detail", "error fetching course from cloud", error)
                _onCourseFetchingError.value = true
                return@addSnapshotListener
            }
            if (value != null){
                val documents = value.documents
                var course: Course?
                documents.forEach{
                    course = it.toObject(Course::class.java)
                    if (course != null){
                        _course.value = course!!
                    }
                }
            }
        }
    }

    fun onCourseFetchingDone(){
        _onCourseFetchingError.value = false
    }

    fun addToCart(){
        if (user.courseOnCart.isNullOrEmpty()){
            user.courseOnCart = arrayListOf(id)
        } else {
            user.courseOnCart!!.add(id)
        }

        db.collection("users").document(user.id).set(user, SetOptions.merge()).addOnSuccessListener {
            viewModelScope.launch {
                userDatabase.update(user)
            }
            _notifyAddedToCart.value = Pair(first = true, second = false)
        }
    }

    fun buyNow(){
        if (user.courseOnCart.isNullOrEmpty()){
            user.courseOnCart = arrayListOf(id)
        } else {
            user.courseOnCart!!.add(id)
        }

        db.collection("users").document(user.id).set(user).addOnSuccessListener {
            viewModelScope.launch {
                userDatabase.update(user)
            }
            _notifyAddedToCart.value = Pair(first = true, second = true)
        }
    }

    fun notified() {
        _notifyAddedToCart.value = null
    }
}