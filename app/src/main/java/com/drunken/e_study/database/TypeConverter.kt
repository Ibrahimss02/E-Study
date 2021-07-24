package com.drunken.e_study.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter{
    @TypeConverter
    fun fromString(value : String?) : ArrayList<String>?{
        val listType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list : ArrayList<String>?) : String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromList(list : List<String>?) : String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringMap(map: List<Map<String, String>>?): String? {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun fromStringToMap(value : String?) : List<Map<String, String>>? {
        val listType = object : TypeToken<List<Map<String, String>>>(){}.type
        return Gson().fromJson(value, listType)
    }
}