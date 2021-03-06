package com.drunken.e_study.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import com.drunken.e_study.dao.CourseDatabaseDao
import com.drunken.e_study.dao.UserDatabaseDao
import com.drunken.e_study.dto.Course
import com.drunken.e_study.dto.User

@Database(entities = [Course::class, User::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase(){

    abstract val courseDatabaseDAO : CourseDatabaseDao
    abstract val userDatabaseDao : UserDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : com.drunken.e_study.database.Database? = null

        fun getInstance(context: Context) : com.drunken.e_study.database.Database {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.drunken.e_study.database.Database::class.java,
                        "user_course_database"
                    )
                        .fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}