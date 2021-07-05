package com.drunken.e_study.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.drunken.e_study.UserRegisterFragment
import com.drunken.e_study.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreUtil {

    private var db = Firebase.firestore

    fun registerUser(userInfo : User, fragment : Fragment){
        db.collection("users").document(getCurrentUserId()).set(userInfo, SetOptions.merge()).addOnSuccessListener {
            (fragment as UserRegisterFragment).registerSuccess(userInfo)
        }.addOnFailureListener {
            Toast.makeText(fragment.requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getCurrentUserId() : String{
        val currentUser = Firebase.auth.currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }
}