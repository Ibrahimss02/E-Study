package com.drunken.e_study.mainScreens.account

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.database.Course
import com.drunken.e_study.database.User
import com.drunken.e_study.database.UserDatabaseDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(private val userDatabase: UserDatabaseDao) : ViewModel() {

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

    private val _idList = MutableLiveData<ArrayList<String>>()
    val idList : LiveData<ArrayList<String>>
    get() = _idList

    private val _signOut = MutableLiveData(false)
    val signOut : LiveData<Boolean>
    get() = _signOut

    private val _showProgressDialog = MutableLiveData(false)
    val showProgressDialog: LiveData<Boolean>
        get() = _showProgressDialog

    init {
        _showProgressDialog.value = true
        viewModelScope.launch {
            getUser()
            Log.i("id list", _idList.value.toString())
        }
    }

    // TODO 1 (Sort all courses into 1 collection for 1 query place at a time)

    fun setupCourses() {
        val courses = ArrayList<Course>()
        db.collection(SD_PATH).whereIn("id", _idList.value!!).addSnapshotListener { value, error ->
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
        db.collection(SMP_PATH).whereIn("id", _idList.value!!).addSnapshotListener { value, error ->
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
        Log.i("observe", _courses.value.toString())
    }

    private suspend fun getUser() {
        val idListTemp = arrayListOf<String>()
        viewModelScope.launch{
            _user.value = userDatabase.lastCurrentUser()
            Log.i("observe", _user.value.toString())
            if (_user.value != null){
                user.value?.coursesId?.forEach {
                    idListTemp.add(it)
                }
            }
            _idList.value = idListTemp
            _showProgressDialog.value = false
        }
    }

    fun signOut(){
        viewModelScope.launch(Dispatchers.Main){
            auth.signOut()
            userDatabase.clearAllUser()
            _signOut.value = true
        }
    }

    fun doneSigningOut(){
        _signOut.value = false
    }
}