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

}