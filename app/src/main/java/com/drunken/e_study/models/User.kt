package com.drunken.e_study.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id : String?,
    val username : String?,
    val email : String?,
    val imageProfile : Int? = null,
    val coursesId : ArrayList<String>? = null
) : Parcelable
