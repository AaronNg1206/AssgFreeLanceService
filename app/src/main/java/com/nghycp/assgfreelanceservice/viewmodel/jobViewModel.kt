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


    private val jobRepository: JobRepository
    var selectedJob: ModelJob =ModelJob("","","","","","","","")


    init {
        val JobDao = JobDatabase.getDatabase(application).JobDao()
        jobRepository = JobRepository(JobDao)


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



}