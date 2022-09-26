package com.nghycp.assgfreelanceservice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.google.firebase.database.FirebaseDatabase
import com.nghycp.assgfreelanceservice.dao.JobDao
import com.nghycp.assgfreelanceservice.model.Job

class JobRepository(private val jobDao: JobDao )  {
    //Cache copy of dataset
val allJob: LiveData<List<Job>> = jobDao.getAll()

suspend fun insert(job: Job){
    jobDao.insert(job)
}

suspend fun delete(job: Job){
    jobDao.delete(job)
}

suspend fun update(job: Job){
    jobDao.update(job)
}

fun findByTitle(title: String): Job{
    return jobDao.findByTitle(title)
}

fun syncJob(id: String){
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("job")

    val jobList = jobDao.getAll()
    if(!jobList.value.isNullOrEmpty()){
        for(job in jobList.value!!.listIterator()){

            myRef.child(id).child(job.title)
                .child("title")
                .setValue(job.title)

            myRef.child(id).child(job.description)
                .child("description")
                .setValue(job.description)

            myRef.child(id).child(job.state)
                .child("state")
                .setValue(job.state)
        }
    }else{
        Log.d("SyncJob", "all Job is null or empty")
    }



}
}