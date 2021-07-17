package com.drunken.e_study.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course : Course)

    @Update
    suspend fun update(course : Course)

    @Query("SELECT * from course_table WHERE id = :key")
    suspend fun getCourse(key: String): Course

    @Query("DELETE from course_table")
    suspend fun clearCourse()

    @Query("SELECT * FROM course_table ORDER BY id DESC")
    fun getAllCourses(): LiveData<List<Course>>
}