package com.drunken.e_study.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "course_table")
data class Course(
    @PrimaryKey
    var id : String = "",

    @ColumnInfo(name = "course_title")
    var title : String? = null,

    @ColumnInfo(name = "course_desc")
    var desc : String? = null,

    @ColumnInfo(name = "course_mentor")
    var mentor : String? = null,

    @ColumnInfo(name = "course_class_category")
    var classCategory : String? = null,

    @ColumnInfo(name = "course_price")
    var price : Long = 0,

    @ColumnInfo(name = "course_rating")
    var rating : Float = 0F,

    @ColumnInfo(name = "course_image")
    var courseImg : Int = 0
)
