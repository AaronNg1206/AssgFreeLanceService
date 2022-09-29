package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityJobShowBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobApplied : AppCompatActivity() {
    private lateinit var binding: ActivityJobShowBinding

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var jobShowAdapter: JobShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadJob()

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loadJob() {
        jobArrayList = ArrayList()
        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Job")
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
                jobShowAdapter = JobShowAdapter(this@JobApplied, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobShow.adapter = jobShowAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }



}