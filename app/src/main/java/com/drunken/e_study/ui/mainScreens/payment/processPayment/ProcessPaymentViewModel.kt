package com.drunken.e_study.ui.mainScreens.payment.processPayment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drunken.e_study.dao.UserDatabaseDao
import com.drunken.e_study.dto.User
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProcessPaymentViewModel(private val userDatabaseDao: UserDatabaseDao) : ViewModel() {

    private val firestore = Firebase.firestore

    private val _paymentProcessingFinished = MutableLiveData<Boolean?>()
    val paymentProcessingFinished: LiveData<Boolean?>
        get() = _paymentProcessingFinished

    private val _onActionConfirm = MutableLiveData<Boolean?>()
    val onActionConfirm: LiveData<Boolean?>
        get() = _onActionConfirm

    init {
        updateUserCourse()
    }

    private fun updateUserCourse() {
        viewModelScope.launch(Dispatchers.Default) {
            val user = userDatabaseDao.lastCurrentUser()
            val courses = arrayListOf<String>()

            /**
             * Check if user course is empty to prevent null pointer exception
             */
            if (user.coursesId.isNullOrEmpty()){
                user.courseOnCart!!.forEach {
                    courses.add(it)
                }
                user.coursesId = courses
            } else {
                user.courseOnCart!!.forEach {
                    user.coursesId!!.add(it)
                }
            }

            user.courseOnCart!!.clear()
            firestore.collection("users").document(user.id).set(user).addOnSuccessListener {
                viewModelScope.launch {
                    userDatabaseDao.update(user)
                    _paymentProcessingFinished.value = true
                }
            }
        }

    }

    fun onConfirmClicked() {
        Log.i("btn", "halo")
        _onActionConfirm.value = true
    }

    fun fragmentNavigated() {
        _paymentProcessingFinished.value = null
        _onActionConfirm.value = null
    }

}