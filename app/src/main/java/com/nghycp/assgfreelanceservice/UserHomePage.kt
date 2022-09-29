package com.nghycp.assgfreelanceservice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityUserHomePageBinding
import com.nghycp.assgfreelanceservice.model.ModelJob


class UserHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityUserHomePageBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var jobArrayList: ArrayList<ModelJob>

    private lateinit var jobShowAdapter: JobShowAdapter

    private lateinit var imageIdList : ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadJob()

        binding.buttonBrowseJob.setOnClickListener {
            startActivity(Intent(this, JobShowActivity::class.java))
        }
        binding.buttonAddNewJob.setOnClickListener {

            startActivity(Intent(this, CustomerAddActivity::class.java))
        }
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.imageViewKl.setOnClickListener {

            var intent = Intent(this,JobShowActivity::class.java)
            startActivity(intent)
        }
        binding.imageViewJohor.setOnClickListener {

            var intent = Intent(this,JobShowActivity::class.java)
            startActivity(intent)
        }

    }


    private fun checkUser() {
        val firebaseUser  = firebaseAuth.currentUser
        if (firebaseUser == null){
            binding.textEmail.text = "Not Logged in"
        }
        else {
            val email = firebaseUser.email

            binding.textEmail.text = email
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.btn_logout_menu -> {
                startActivity(Intent(this,LoginActivity::class.java))
            }
            R.id.btn_userprofile -> {
                startActivity(Intent(this,UserProfileActivity::class.java))
            }

        }

        return super.onOptionsItemSelected(item)
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
                jobShowAdapter = JobShowAdapter(this@UserHomePage, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobShow.adapter = jobShowAdapter

            jobShowAdapter.setOnItemClickListener(object :JobShowAdapter.onItemClicklistener{
                override fun onItemClick(position: Int){


                }
            })

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}