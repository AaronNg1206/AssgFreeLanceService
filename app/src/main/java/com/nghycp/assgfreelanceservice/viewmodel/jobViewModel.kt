package com.nghycp.assgfreelanceservice.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.nghycp.assgfreelanceservice.database.JobDatabase
import com.nghycp.assgfreelanceservice.model.Job
import com.nghycp.assgfreelanceservice.repository.JobRepository
import kotlinx.coroutines.launch

class jobViewModel (application: Application) :
    AndroidViewModel(application){
    //Create a UI dataset
    val jobList: LiveData<List<Job>>

    private val jobRepository: JobRepository
    var selectedJob:Job =Job("","","","","","","","")


    init {
        val JobDao = JobDatabase.getDatabase(application).JobDao()
        jobRepository = JobRepository(JobDao)
        jobList = jobRepository.allJob

        val preferences = application.getSharedPreferences(
            application.applicationContext.packageName.toString(), Context.MODE_PRIVATE
        )


    }

    //Launching a coroutine
    fun insert(job: Job) = viewModelScope.launch {
        jobRepository.insert(job)
    }

    fun delete(job: Job) = viewModelScope.launch {
        jobRepository.delete(job)
    }

    fun update(job: Job) = viewModelScope.launch {
        jobRepository.update(job)
    }

    fun syncJob(id: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("job")

        /*val jobList = jobDao.getAll()*/
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
        }else
            {
            Toast.makeText(getApplication(), "Job List is empty", Toast.LENGTH_SHORT).show()
            Log.d("SyncJob", "allJob is null or empty")
        }
    }

}