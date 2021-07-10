package com.drunken.e_study.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id : String? = null,
    val username : String? = null,
    val email : String? = null,
    val imageProfile : Int? = null,
    val coursesId : ArrayList<String>? = null
) : Parcelable
