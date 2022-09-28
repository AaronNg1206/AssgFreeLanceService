package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityAdminShowBinding
import java.lang.Exception

class AdminShow : AppCompatActivity() {

    private lateinit var binding: ActivityAdminShowBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold job
    private lateinit var jobArrayList: ArrayList<ModelJob>

    //adapter
    private lateinit var adapterJob: AdapterJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadJob()

        binding.searchEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    adapterJob.filter.filter(s)
                }
                catch(e: Exception){

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }

    private fun loadJob() {
        //init arraylist
        jobArrayList = ArrayList()
        //get all the job from database
        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Job")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before start
                jobArrayList.clear()
                for(ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelJob::class.java)
                    //add to array list
                    jobArrayList.add(model!!)
                }
                adapterJob = AdapterJob(this@AdminShow,jobArrayList)

                binding.jobRV.adapter = adapterJob
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}