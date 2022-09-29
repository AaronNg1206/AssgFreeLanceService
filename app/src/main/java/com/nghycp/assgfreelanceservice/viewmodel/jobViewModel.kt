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
import com.nghycp.assgfreelanceservice.model.ModelJob
import com.nghycp.assgfreelanceservice.repository.JobRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class jobViewModel (application: Application) :
    AndroidViewModel(application){
    //Create a UI dataset
    val jobList: LiveData<List<ModelJob>>

    private val jobRepository: JobRepository
    var selectedJob: ModelJob =ModelJob("","","","","","","","")


    init {
        val JobDao = JobDatabase.getDatabase(application).JobDao()
        jobRepository = JobRepository(JobDao)
        jobList = jobRepository.allJob

        val preferences = application.getSharedPreferences(
            application.applicationContext.packageName.toString(), Context.MODE_PRIVATE
        )


    }

    //Launching a coroutine
    fun insert(job: ModelJob) = viewModelScope.launch {
        jobRepository.insert(job)
    }

    fun delete(job: ModelJob) = viewModelScope.launch {
        jobRepository.delete(job)
    }

    fun update(job: ModelJob) = viewModelScope.launch {
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

                myRef.child(id).child(job.Description)
                    .child("Description")
                    .setValue(job.Description)

                myRef.child(id).child(job.Salary)
                    .child("state")
                    .setValue(job.Salary)
            }
        }else
            {
            Toast.makeText(getApplication(), "Job List is empty", Toast.LENGTH_SHORT).show()
            Log.d("SyncJob", "allJob is null or empty")
        }
    }

}