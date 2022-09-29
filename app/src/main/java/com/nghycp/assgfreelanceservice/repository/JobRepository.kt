package com.nghycp.assgfreelanceservice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.google.firebase.database.FirebaseDatabase
import com.nghycp.assgfreelanceservice.dao.JobDao
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobRepository(private val jobDao: JobDao )  {

    suspend fun insert(job: ModelJob){
        jobDao.insert(job)
    }

    suspend fun delete(job: ModelJob){
        jobDao.delete(job)
    }

    suspend fun update(job: ModelJob){
        jobDao.update(job)
    }

}