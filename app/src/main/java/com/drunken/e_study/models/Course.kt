package com.drunken.e_study.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    var id : String? = null,
    var title : String? = null,
    var desc : String? = null,
    var mentor : String? = null,
    var classCategory : String? = null,
    var price : Long = 0,
    var rating : Float = 0F,
    var courseImg : Int = 0
) : Parcelable
