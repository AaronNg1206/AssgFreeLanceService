package com.nghycp.assgfreelanceservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
//import
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityUserProfileBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        binding.backIconBtn.setOnClickListener{
            onBackPressed()
        }

        binding.editIconBtn.setOnClickListener {
            startActivity(Intent(this, EditUserProfile::class.java))
        }

    }

    private fun loadUserInfo() {
        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phoneNum = "${snapshot.child("phoneNum").value}"
                    val userType = "${snapshot.child("userType").value}"
                    val uid = "${snapshot.child("uid").value}"

                    binding.disname.text = name
                    binding.disemail.text = email
                    binding.disnum.text = phoneNum
                    binding.distype.text = userType

                    try {
                        Glide.with(this@UserProfileActivity)
                            .load(profileImage)
                            .placeholder(R.drawable.user)
                            .into(binding.imageEdit)
                    }
                    catch (e: Exception){

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}