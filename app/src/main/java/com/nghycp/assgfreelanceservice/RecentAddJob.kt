package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityRecentAddJobBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class RecentAddJob : AppCompatActivity() {
    private lateinit var binding: ActivityRecentAddJobBinding

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var RecentAddJobAdapter: RecentAddJobAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_add_job)

        val myData = intent.extras?.getString("uid")

//        Log.v("MainDAta1", myData.toString())
//        if (myData != null) {
//            Log.v("MainDAta2",myData)
//        }
        if (myData != null) {
            loadJob(myData)
        }

    }
    private fun loadJob(uidOnClick : String) {
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
                Log.v("RecentAdd1",jobArrayList.get(0).uid)

                Log.v("RecentAdd2",uidOnClick)
                for (i in jobArrayList.indices) {
                    Log.d("RecentAdd3",jobArrayList.get(i).uid)
                    Log.d("RecentAdd4",uidOnClick)
                    if (jobArrayList.get(i).uid == uidOnClick) {
                        jobArrayList.add(jobArrayList.get(i))
                        ref.child(uidOnClick)
                            .setValue(jobArrayList)
                            .addOnSuccessListener {
                                Toast.makeText(applicationContext, "Job", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(
                                    applicationContext,
                                    "unable to apply due to ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    } else if (jobArrayList.get(i).id != uidOnClick) { }
                }
                //set adapter

                RecentAddJobAdapter = RecentAddJobAdapter(this@RecentAddJob, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewRecentAdd.adapter = RecentAddJobAdapter
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}