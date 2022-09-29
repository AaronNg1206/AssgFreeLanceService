package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityAdminShowBinding

class AdminShow : AppCompatActivity() {

    private lateinit var binding: ActivityAdminShowBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var adapterJob: AdapterJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadJob()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun loadJob() {
        jobArrayList = ArrayList()

        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Job")
        ref.addValueEventListener(object : ValueEventListener{
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
                adapterJob = AdapterJob(this@AdminShow, jobArrayList)
                //set adapter to recycle view
                binding.jobRv.adapter = adapterJob
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}