package com.nghycp.assgfreelanceservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.nghycp.assgfreelanceservice.databinding.ActivityUserHomePageBinding

class UserHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityUserHomePageBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnEditUser.setOnClickListener {
            startActivity(Intent(this,UserProfileActivity::class.java))
        }

        binding.LogOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
//            finish()
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
}