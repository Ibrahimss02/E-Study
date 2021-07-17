package com.drunken.e_study.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class Course(
    @PrimaryKey
    val id : String = "",

    @ColumnInfo(name = "course_title")
    val title : String? = null,

    @ColumnInfo(name = "course_desc")
    val desc : String? = null,

    @ColumnInfo(name = "course_mentor")
    val mentor : String? = null,

    @ColumnInfo(name = "course_class_category")
    val classCategory : String? = null,

    @ColumnInfo(name = "course_price")
    val price : Long? = 0,

    @ColumnInfo(name = "course_rating")
    val rating : Float? = 0F,

    @ColumnInfo(name = "course_image")
    val courseImg : Int? = 0,

    @ColumnInfo(name = "course_videos")
    val videos : List<String>? = null,

    @ColumnInfo(name = "course_modules")
    val modules : List<String>? = null,

    @ColumnInfo(name = "course_quiz")
    val listQuiz : List<String>? = null,

    @ColumnInfo(name = "course_student")
    val totalStudent : Int? = 0
)
