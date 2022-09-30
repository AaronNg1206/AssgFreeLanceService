package com.nghycp.assgfreelanceservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityJobShowBinding
import com.nghycp.assgfreelanceservice.model.ModelJob


class JobShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobShowBinding

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var filterlist: ArrayList<ModelJob>
    private lateinit var jobShowAdapter: JobShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadJob()
        val myData = intent.extras?.getString("State")
        if (myData != null) {
            filterJob(myData)
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun filterJob(stringState: String) {
        jobArrayList = ArrayList()
        filterlist = ArrayList()
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

                for ( i in  jobArrayList.indices){
                    if (jobArrayList.get(i).State == stringState){
                        filterlist.add(jobArrayList.get(i))
                    }
                }
                jobShowAdapter = JobShowAdapter(this@JobShowActivity, filterlist)
                binding.recyclerviewJobShow.adapter = jobShowAdapter
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun loadJob(){
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
                jobShowAdapter = JobShowAdapter(this@JobShowActivity, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobShow.adapter = jobShowAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}


