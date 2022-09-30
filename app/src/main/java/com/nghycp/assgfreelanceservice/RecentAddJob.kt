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

class RecentAddJob : AppCompatActivity() {
    private lateinit var binding: ActivityJobAppliedBinding

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var JobApplyadapter: JobApplyadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_add_job)
        loadJob()
        //filterJob()
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
                JobApplyadapter = JobApplyadapter(this@RecentAddJob, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobApply.adapter = JobApplyadapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}