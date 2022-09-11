package com.nghycp.assgfreelanceservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nghycp.assgfreelanceservice.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_user_profile)

        binding.btnBackHome.setOnClickListener {
            startActivity(Intent(this, UserHomePage::class.java))
        }
    }
}