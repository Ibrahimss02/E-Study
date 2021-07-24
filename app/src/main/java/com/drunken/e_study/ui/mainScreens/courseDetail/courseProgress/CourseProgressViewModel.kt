package com.drunken.e_study.ui.mainScreens.courseDetail.courseProgress

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drunken.e_study.dto.Course
import com.drunken.e_study.ui.mainScreens.courseDetail.CourseDetailFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseProgressViewModel(private val id : String = "", val type : Int) : ViewModel() {

    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course>
    get() = _course

    private val _title = MutableLiveData<List<String>>()
    val title : LiveData<List<String>>
    get() = _title

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
                _title.value = when(type){
                    CourseDetailFragment.VIDEOS_ITEM_TYPE -> {
                        _course.value?.videos
                    }
                    CourseDetailFragment.MODULES_ITEM_TYPE -> {
                        _course.value?.modules
                    }
                    CourseDetailFragment.QUIZ_ITEM_TYPE -> {
                        _course.value?.listQuiz
                    }
                    else -> emptyList()
                }
            }
        }
    }
}