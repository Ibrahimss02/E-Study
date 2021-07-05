package com.drunken.e_study.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    val id : String,
    val tittle : String,
    val desc : String,
    val mentor : String,
    val classCategory : String,
    val price : Long,
    val rating : Float,
    val courseImg : Int
) : Parcelable
