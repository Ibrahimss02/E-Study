package com.drunken.e_study.utils

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.drunken.e_study.database.Course
import com.drunken.e_study.welcomeScreens.UserRegisterFragment
import com.drunken.e_study.database.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class FirestoreUtil {

    private var db = Firebase.firestore
    private var auth = Firebase.auth
    private val _courses = MutableLiveData<ArrayList<Course>>()
    val courses : LiveData<ArrayList<Course>>
    get() = _courses


    fun registerUser(userInfo : User, fragment : Fragment){
        db.collection("users").document(getCurrentUserId()).set(userInfo, SetOptions.merge()).addOnSuccessListener {
            (fragment as UserRegisterFragment).registerSuccess(userInfo)
        }.addOnFailureListener {
            Toast.makeText(fragment.requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getCurrentUserId() : String{
        val currentUser = auth.currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun getCurrentUserCoursesId() : ArrayList<String> {
        val list = ArrayList<String>()
        db.collection("users").document(getCurrentUserId()).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            if (user != null){
                user.coursesId!!.forEach { id ->
                    list.add(id)
                }
            }
        }
        return list
    }

    fun getCourseBelong(idList: List<String>){
        db.collection(SD_PATH).whereIn("id", idList).addSnapshotListener { value, error ->
            if (error != null){
                Log.w(TAG, "listen failed", error)
                return@addSnapshotListener
            }
            if (value != null){
                val courses = ArrayList<Course>()
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
            if (error != null) {
                Log.w(TAG, "listen failed", error)
                return@addSnapshotListener
            }
            if (value != null) {
                val courses = ArrayList<Course>()
                val documents = value.documents
                documents.forEach {
                    val course = it.toObject(Course::class.java)
                    if (course != null) {
                        courses.add(course)
                    }
                }
                _courses.value = courses
            }
        }
    }

    companion object {
        private const val SD_PATH = "SD_courses"
        private const val SMP_PATH = "SMP_courses"
    }
}