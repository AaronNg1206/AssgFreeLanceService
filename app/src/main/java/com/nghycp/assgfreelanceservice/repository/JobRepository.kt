package com.nghycp.assgfreelanceservice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.google.firebase.database.FirebaseDatabase
import com.nghycp.assgfreelanceservice.dao.JobDao
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobRepository(private val jobDao: JobDao )  {
    //Cache copy of dataset
val allJob: LiveData<List<ModelJob>> = jobDao.getAll()

suspend fun insert(job: ModelJob){
    jobDao.insert(job)
}

suspend fun delete(job: ModelJob){
    jobDao.delete(job)
}

suspend fun update(job: ModelJob){
    jobDao.update(job)
}

/*fun findByTitle(title: String): ModelJob{
    return jobDao.findByTitle(title)
}*/

fun syncJob(id: String){
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("job")

    val jobList = jobDao.getAll()
    if(!jobList.value.isNullOrEmpty()){
        for(job in jobList.value!!.listIterator()){

            myRef.child(id).child(job.title)
                .child("title")
                .setValue(job.title)

            myRef.child(id).child(job.Description)
                .child("Description")
                .setValue(job.Description)

            myRef.child(id).child(job.State)
                .child("State")
                .setValue(job.State)
        }
    }else{
        Log.d("SyncJob", "all Job is null or empty")
    }



}
}