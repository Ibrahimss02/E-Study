package com.drunken.e_study.ui.mainScreens.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dao.UserDatabaseDao
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dto.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeViewModel(private val database : UserDatabaseDao, private val courseDatabase : CourseDatabaseDao) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
    get() = _user

    private val firestore = Firebase.firestore

    companion object {
        const val SD_PATH = "SD_courses"
        const val SMP_PATH = "SMP_courses"
        const val SMA_PATH = "SMA_courses"
        const val KULIAH_PATH = "Kuliah_courses"
    }

    init {
        viewModelScope.launch {
            _user.postValue(database.lastCurrentUser())
            fetchCourseToDb()
        }

    }

    /**
     * This function is used to fetch all course from cloud to local Room database
     * every time this fragment/ui gets initialized.
     */
    private suspend fun fetchCourseToDb() {
        firestore.collection("courses").addSnapshotListener { value, error ->
            if (error != null){
                Log.w(ContentValues.TAG, "listen failed", error)
            }
            if (value != null){
                val docs = value.documents
                docs.forEach {
                    val course = it.toObject(Course::class.java)
                    viewModelScope.launch {
                        course?.let { newCourse ->
                            courseDatabase.insert(newCourse)
                        }

                    }
                }
            }
        }
    }

    private val _args = MutableLiveData<Pair<String, String>?>()
    val args : LiveData<Pair<String, String>?>
    get() = _args

    fun navigateToBrowse(category : String){
        when(category){
            SD_PATH -> {
                _args.value = Pair(SD_PATH, "Kursus SD")
            }
            SMP_PATH -> {
                _args.value = Pair(SMP_PATH, "Kursus SMP")
            }
            SMA_PATH -> {
                _args.value = Pair(SMA_PATH, "Kursus SMA")
            }
            KULIAH_PATH -> {
                _args.value = Pair(KULIAH_PATH, "Kursus Kuliah")
            }
        }
    }

    fun doneNavigating(){
        _args.value = null
    }
}