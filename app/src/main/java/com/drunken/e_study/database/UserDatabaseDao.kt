package com.drunken.e_study.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user : User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * from user_table WHERE id = :key")
    suspend fun getUser(key: Long) : User

    @Query("SELECT * from user_table LIMIT 1")
    suspend fun getLastCurrentUser() : User

    @Query("DELETE from user_table")
    suspend fun clearAllUser()
}