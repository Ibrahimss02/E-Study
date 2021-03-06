package com.drunken.e_study.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id : String = "",

    @ColumnInfo(name = "user_username")
    val username : String? = null,

    @ColumnInfo(name = "user_email")
    val email : String? = null,

    @ColumnInfo(name = "user_profile_image")
    var imageProfile : String? = null,

    @ColumnInfo(name = "user_courses_id")
    var coursesId : ArrayList<String>? = null,

    @ColumnInfo(name = "user_courses_cart")
    var courseOnCart : ArrayList<String>? = null
)

