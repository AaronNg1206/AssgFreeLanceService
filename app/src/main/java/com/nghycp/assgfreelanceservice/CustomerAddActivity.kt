package com.nghycp.assgfreelanceservice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityCustomerAddBinding

class CustomerAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerAddBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.buttonAdd.setOnClickListener {
            validateAddJob()
        }
        binding.buttonCancel.setOnClickListener {
            onBackPressed()
        }

    }

    private var title = ""
    private var category = ""
    private var description = ""
    private var address = ""
    private var state = ""
    private var salary = ""

    private fun validateAddJob(){
        title = binding.editTextJobTitle.text.toString().trim()
        category = binding.spinnerJobCategory.selectedItem.toString().trim()
        description = binding.editTextJobDescription.text.toString().trim()
        address = binding.editTextAddress.text.toString().trim()
        state = binding.spinnerState.selectedItem.toString().trim()
        salary =binding.editTextNumberSalary.text.toString().trim()

        if (title.isEmpty()){
            Toast.makeText(this, "Enter the job title", Toast.LENGTH_SHORT).show()
        }
        else if (category.isEmpty()){
            Toast.makeText(this, "Choose an category", Toast.LENGTH_SHORT).show()
        }
        else if (description.isEmpty()){
            Toast.makeText(this, "Enter the job description", Toast.LENGTH_SHORT).show()
        }
        else if(state.isEmpty()){
            Toast.makeText(this, "Enter the State of job", Toast.LENGTH_SHORT).show()
        }
        else if(salary.isEmpty()){
            Toast.makeText(this, "Enter the job salary", Toast.LENGTH_SHORT).show()
        }
        else {
            addRecord()
        }
    }

    private fun addRecord() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["title"] = title
        hashMap["category"] = category
        hashMap["Description"] = description
        hashMap["Address"] = address
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
                startActivity(Intent(this, UserHomePage::class.java))

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
}