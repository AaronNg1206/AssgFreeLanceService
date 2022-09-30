package com.nghycp.assgfreelanceservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.nghycp.assgfreelanceservice.databinding.ActivityAdminHomePageBinding

class AdminHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHomePageBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this,AdminAdd::class.java))
        }

        binding.btnShow.setOnClickListener {
            startActivity(Intent(this,AdminShow::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            val email = firebaseUser.email

            binding.emailAdmin.text = email
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.admin_logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}