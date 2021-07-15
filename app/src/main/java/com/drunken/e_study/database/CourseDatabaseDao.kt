package com.drunken.e_study.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CourseDatabaseDao {

    @Insert
    suspend fun insertCourse(course : Course)

    @Update
    suspend fun updateCourse(course : Course)

    @Query("SELECT * from course_table WHERE id = :key")
    fun getCourse(key : Long) : LiveData<Course>

    @Query("DELETE from course_table")
    suspend fun clearCourse()

    @Query("SELECT * FROM course_table")
    fun getAllCourses() : LiveData<List<Course>>
}