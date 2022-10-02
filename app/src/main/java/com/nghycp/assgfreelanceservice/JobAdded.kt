package com.nghycp.assgfreelanceservice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityJobAddedBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobAdded : AppCompatActivity() {

    private lateinit var binding: ActivityJobAddedBinding
    private  var jobArrayList: ArrayList<ModelJob> = ArrayList()
    private  var loadArrayList: ArrayList<ModelJob> = ArrayList()
    private lateinit var RecentAddJobAdapter: RecentAddJobAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_added)


           val myData = intent.extras?.getString("uid")
           if (myData != null) {
               Log.v("MainActivity",myData)
           }
           if (myData != null) {
               loadJob(myData)
           }


    }

    private fun loadJob(myData: String?) {
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
                    if (jobArrayList.get(i).State == myData){
                        loadArrayList.add(jobArrayList.get(i))
                    }
                }
       /*         RecentAddJobAdapter = RecentAddJobAdapter(this@JobAdded, loadArrayList)
                binding.recyclerviewRecentAdd.adapter = RecentAddJobAdapter*/
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

/*        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                jobArrayList.clear()
                for (ds in snapshot.children) {
                    //get data as model
                    val model = ds.getValue(ModelJob::class.java)
                    //add to array list
                    jobArrayList.add(model!!)
                }

                for (i in jobArrayList.indices) {
                    if (jobArrayList.get(i).uid == myData) {
                        jobArrayList.add(jobArrayList.get(i))
                    }
                }


            }
            override fun onCancelled(error: DatabaseError) {

            }

        })*/
    }
