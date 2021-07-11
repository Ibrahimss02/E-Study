package com.drunken.e_study.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id : String = "",

    @ColumnInfo(name = "user_username")
    val username : String? = null,

    @ColumnInfo(name = "user_email")
    val email : String? = null,

    @ColumnInfo(name = "user_profile_image")
    val imageProfile : Int? = null,

    @ColumnInfo(name = "user_courses_id")
    val coursesId : ArrayList<String>? = null
)

class CourseIdTypeConverter{
    @TypeConverter
    fun fromString(value : String?) : ArrayList<String>{
        val listType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list : ArrayList<String?>) : String {
        return Gson().toJson(list)
    }
}
