package com.nghycp.assgfreelanceservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nghycp.assgfreelanceservice.databinding.ActivityAdminAddBinding

private lateinit var binding: ActivityAdminAddBinding

class AdminAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this,AdminHomePage::class.java))
        }

    }
}