package com.nghycp.assgfreelanceservice

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.nghycp.assgfreelanceservice.databinding.ActivityJobShowBinding
import com.nghycp.assgfreelanceservice.model.ModelJob
import com.nghycp.assgfreelanceservice.viewmodel.jobViewModel

class JobShowActivity : AppCompatActivity() {

    private var _binding: ActivityJobShowBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobArrayList: ArrayList<ModelJob>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_show)

        // getting the recyclerview by its id
        val recyclerview = _binding?.recyclerviewJobShow

        // this creates a vertical layout Manager
        binding.recyclerviewJobShow.layoutManager = LinearLayoutManager(this)

        // ArrayList of class jobViewModel
        jobArrayList = ArrayList()

        //This will pass the ArrayList to our Adapter
        val adapter = AdapterJob(this@JobShowActivity, jobArrayList)



        //Setting the Adapter with the recyclerview
        recyclerview?.adapter = adapter
    }


    override fun onStart() {
        Log.d("onStart", "Job Show Activity")
        super.onStart()
    }

    override fun onResume() {
        Log.d("onResume", "Job Show Activity")
        super.onResume()
    }

}