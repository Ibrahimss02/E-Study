package com.drunken.e_study.ui.mainScreens.courseDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dto.Course
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseDetailViewModel(private val id : String = "") : ViewModel(){

    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course>
        get() = _course

    private val db = Firebase.firestore

    init {
        fetchCourseFromCloud(id)
    }

    private fun fetchCourseFromCloud(id: String) {
        db.collection("courses").whereEqualTo("id", id).addSnapshotListener { value, error ->
            if (error != null){
                Log.w("Course Detail", "error fetching course from cloud", error)
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

}