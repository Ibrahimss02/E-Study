package com.drunken.e_study.mainScreens.browseCourse

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drunken.e_study.database.Course
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BrowseCourseViewModel(path : String) : ViewModel() {

    private var db = Firebase.firestore
    private val _courses = MutableLiveData<ArrayList<Course>>()
    val courses : LiveData<ArrayList<Course>>
    get() = _courses

    private val _navigateToCourseDetail = MutableLiveData<String?>()
    val navigateToCourseDetail : LiveData<String?>
    get() = _navigateToCourseDetail

    init {
        getCourse(path)
    }

    private fun getCourse(path : String) {
        val allCourses = ArrayList<Course>()
        db.collection(path).addSnapshotListener { value, error ->
            if (error != null){
                Log.w(ContentValues.TAG, "listen failed", error)
                return@addSnapshotListener
            }
            if (value != null){
                val documents = value.documents
                documents.forEach{
                    val course = it.toObject(Course::class.java)
                    if (course != null){
                        allCourses.add(course)
                    }
                }
                _courses.value = allCourses
            }
        }
    }

    fun onCourseItemClicked(courseId : String){
        _navigateToCourseDetail.value = courseId
    }

    fun onCourseNavigated(){
        _navigateToCourseDetail.value = null
    }

}