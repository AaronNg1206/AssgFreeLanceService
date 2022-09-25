package com.nghycp.assgfreelanceservice.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import  com.nghycp.assgfreelanceservice.dao.JobDao
import  com.nghycp.assgfreelanceservice.model.Job

@Database(entities = arrayOf(Job::class), version = 1, exportSchema = false)
abstract class JobDatabase: RoomDatabase() {
    abstract fun JobDao(): JobDao

    companion object{
        //Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE : JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!= null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    "Job_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}