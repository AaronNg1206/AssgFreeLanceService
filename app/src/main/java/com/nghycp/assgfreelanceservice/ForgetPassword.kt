package com.nghycp.assgfreelanceservice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nghycp.assgfreelanceservice.databinding.ActivityForgetPasswordBinding

class ForgetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.resetBtn.setOnClickListener {
            validateData()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnBackToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private var email = ""
    private fun validateData() {

        email = binding.forgetPassword.text.toString().trim()

        if (email.isEmpty()){
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email pattern...", Toast.LENGTH_SHORT).show()
        }
        else {
            recoverPassword()
        }

    }

    private fun recoverPassword() {

        progressDialog.setMessage("Sending password reset instructions to $email")
        progressDialog.show()
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener{
                progressDialog.dismiss()
                Toast.makeText(this, "Instructions sent to \n$email...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to send due to the email...", Toast.LENGTH_SHORT).show()
            }
    }
}