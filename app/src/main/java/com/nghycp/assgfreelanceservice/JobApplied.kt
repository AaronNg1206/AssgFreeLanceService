package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityJobAppliedBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobApplied : AppCompatActivity() {
    private lateinit var binding: ActivityJobAppliedBinding

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var JobApplyadapter: JobApplyadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobAppliedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadJob()

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loadJob() {
        jobArrayList = ArrayList()
        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("JobApply")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                jobArrayList.clear()
                for(ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelJob::class.java)

                    //add to array list
                    jobArrayList.add(model!!)
                }
                //set adapter
                JobApplyadapter = JobApplyadapter(this@JobApplied, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobApply.adapter = JobApplyadapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }



}