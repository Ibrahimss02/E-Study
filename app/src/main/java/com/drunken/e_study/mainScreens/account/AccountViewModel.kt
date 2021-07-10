package com.drunken.e_study.mainScreens.account

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.models.Course
import com.drunken.e_study.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    companion object {
        private const val SD_PATH = "SD_courses"
        private const val SMP_PATH = "SMP_courses"
    }

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
    get() = _user

    private val _courses = MutableLiveData<ArrayList<Course>>()
    val courses : LiveData<ArrayList<Course>>
    get() = _courses

    private val idList = ArrayList<String>()

    private val _signOut = MutableLiveData<Boolean>(false)
    val signOut : LiveData<Boolean>
    get() = _signOut

    private val _notifyEmptyCourse = MutableLiveData<Boolean>(false)
    val notifyEmptyCourse : LiveData<Boolean>
    get() = _notifyEmptyCourse

    init {
        _notifyEmptyCourse.value = false
        _signOut.value = false
        viewModelScope.launch {
            getUser()
            delay(5000)
            if (idList.isNotEmpty()) getCourses()
            else {
                _notifyEmptyCourse.value = true
            }
        }
    }

    // TODO 1 (Sort all courses into 1 collection for 1 query place at a time)

    private fun getCourses() {
        val courses = ArrayList<Course>()
        db.collection(SD_PATH).whereIn("id", idList).addSnapshotListener { value, error ->
            if (error != null){
                Log.w(ContentValues.TAG, "listen failed", error)
            }
            if (value != null){
                val documents = value.documents
                documents.forEach{
                    val course = it.toObject(Course::class.java)
                    if (course != null){
                        courses.add(course)
                    }
                }
                _courses.value = courses
            }
        }
        db.collection(SMP_PATH).whereIn("id", idList).addSnapshotListener { value, error ->
            if (error != null){
                Log.w(ContentValues.TAG, "listen failed", error)
            }
            if (value != null){
                val documents = value.documents
                documents.forEach{
                    val course = it.toObject(Course::class.java)
                    if (course != null){
                        courses.add(course)
                    }
                }
                _courses.value = courses
            }
        }
    }

    private fun getUser() {
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            if (user != null){
                _user.value = user
                user.coursesId?.let { list ->
                    list.forEach { id ->
                        idList.add(id)
                    }
                }
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _signOut.value = true
    }

    fun doneSigningOut(){
        _signOut.value = null
    }

    fun doneNotifying(){
        _notifyEmptyCourse.value = null
    }
}