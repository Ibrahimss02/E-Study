package com.drunken.e_study.mainScreens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.drunken.e_study.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragmentViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
    get() = _user

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    companion object {
        const val SD_PATH = "SD_courses"
        const val SMP_PATH = "SMP_courses"
        const val SMA_PATH = "SMA_courses"
        const val KULIAH_PATH = "Kuliah_courses"
    }

    init {
        getUser()
    }

    private fun getUser() {
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            if (user != null){
                _user.value = user
            }
        }
    }

    private val _args = MutableLiveData<List<String>>()
    val args : LiveData<List<String>>
    get() = _args

    fun navigateToBrowse(category : String){
        when(category){
            SD_PATH -> {
                _args.value = listOf(SD_PATH, "Kursus SD")
            }
            SMP_PATH -> {
                _args.value = listOf(SMP_PATH, "Kursus SMP")
            }
            SMA_PATH -> {
                _args.value = listOf(SMA_PATH, "Kursus SMA")
            }
            KULIAH_PATH -> {
                _args.value = listOf(KULIAH_PATH, "Kursus Kuliah")
            }
        }
    }
}