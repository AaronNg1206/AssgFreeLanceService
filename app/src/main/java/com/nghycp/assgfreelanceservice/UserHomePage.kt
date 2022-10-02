package com.nghycp.assgfreelanceservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
            val filterlist = binding.textViewKL.text.toString()
            //Log.v("MainActivity", stateKL);
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewPerak.setOnClickListener {
            val filterlist = binding.textViewPerak.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewTerengganu.setOnClickListener {
            val filterlist = binding.textViewTerengganu.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewJohor.setOnClickListener {
            val filterlist = binding.textViewJohor.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewKedah.setOnClickListener {
            val filterlist = binding.textViewKedah.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewLangkawi.setOnClickListener {
            val filterlist = binding.textViewLangkawi.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewPenang.setOnClickListener {
            val filterlist = binding.textViewPenang.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
            startActivity(intent)
        }
        binding.imageViewMelaka.setOnClickListener {
            val filterlist = binding.textViewMelaka.text.toString()
            var intent = Intent(this,JobShowActivity::class.java).also {
                it.putExtra("State", filterlist)
            }
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
            R.id.RecentApplyJob -> {
                startActivity(Intent( this,JobApplied::class.java))
            }
            R.id.RecentAddJob -> {
              val firebaseUser  = firebaseAuth.currentUser?.uid

                var intent = Intent( this,JobAdded::class.java)
                    .also {it.putExtra("uid", firebaseUser)}
                startActivity(intent)

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
                //Log.v("MainActivity",jobArrayList.toString())
                //set adapter
                jobShowAdapter = JobShowAdapter(this@UserHomePage, jobArrayList)
                //set adapter to recycle view
                binding.recyclerviewJobShow.adapter = jobShowAdapter



            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}