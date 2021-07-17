package com.drunken.e_study.ui.mainScreens.courseDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drunken.e_study.database.Course
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseDetailViewModel(
    id : String = "",
) : ViewModel(){

    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course>
    get() = _course

    private val _onCourseFetchingError = MutableLiveData(false)
    val onCourseFetchingError : LiveData<Boolean>
    get() = _onCourseFetchingError

    private val db = Firebase.firestore

    init {
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
        // TODO (Implement add to cart feature)
    }

    fun buyNow(){
        // TODO (Implement buy now feature)
    }
}