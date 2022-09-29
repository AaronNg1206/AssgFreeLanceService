package com.nghycp.assgfreelanceservice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityAdminAddBinding

private lateinit var binding: ActivityAdminAddBinding

private lateinit var firebaseAuth: FirebaseAuth

private lateinit var progressDialog: ProgressDialog

class AdminAdd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.button.setOnClickListener {
            validateData()
        }

    }

    private var title = ""
    private var category = ""
    private var desc = ""
    private var add = ""
    private var state = ""
    private var salary = ""

    private fun validateData() {

        title = binding.editjobtitle.text.toString().trim()
        category = binding.spinnerJobCategory.selectedItem.toString().trim()
        desc = binding.editdesc.text.toString().trim()
        add = binding.editaddress.text.toString().trim()
        state = binding.spinnerState.selectedItem.toString().trim()
        salary = binding.editsalary.text.toString().trim()

        //validate data
        if (title.isEmpty()){
            Toast.makeText(this, "Enter the job title", Toast.LENGTH_SHORT).show()
        }
        else if (category.isEmpty()){
            Toast.makeText(this, "Choose an category", Toast.LENGTH_SHORT).show()
        }
        else if (desc.isEmpty()){
            Toast.makeText(this, "Enter the job description", Toast.LENGTH_SHORT).show()
        }
        else if(state.isEmpty()){
            Toast.makeText(this, "Enter the State of job", Toast.LENGTH_SHORT).show()
        }
        else if(salary.isEmpty()){
            Toast.makeText(this, "Enter the job salary", Toast.LENGTH_SHORT).show()
        }
        else{
            addFirebase()
        }

    }

    private fun addFirebase() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["title"] = title
        hashMap["category"] = category
        //hashMap["timestamp"] = timestamp
        hashMap["Description"] = desc
        hashMap["Address"] = add
        hashMap["State"] = state
        hashMap["Salary"] = salary
        hashMap["uid"] = "${firebaseAuth.uid}"

        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Job")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Added Successfully...",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminHomePage::class.java))
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }
}