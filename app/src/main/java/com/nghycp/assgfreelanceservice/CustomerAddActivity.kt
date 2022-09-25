package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nghycp.assgfreelanceservice.database.JobDatabase
import com.nghycp.assgfreelanceservice.databinding.ActivityCustomerAddBinding
import kotlinx.coroutines.Job

class CustomerAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerAddBinding

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            val title =binding.editTextJobTitle.text.toString()
            val category = binding.spinnerJobCategory.toString()
            val description = binding.editTextJobDescription.text.toString()
            val dueDate = binding.editTextDate.text.toString()
            val address = binding.editTextAddress.text.toString()
            val state = binding.spinnerState.toString()
            val salary =binding.editTextNumberSalary.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Job")

            database.child(title).setValue(title).addOnSuccessListener {
                binding.editTextJobTitle.text.clear()
                binding.spinnerJobCategory
                binding.editTextJobDescription.text.clear()
                binding.editTextDate.text.clear()
                binding.editTextAddress.text.clear()
                binding.spinnerState
                binding.editTextNumberSalary.text.clear()

                Toast.makeText(this, "Sucessfull Added", Toast.LENGTH_SHORT).show()
                
                
            }.addOnFailureListener{
                Toast.makeText(this, "Failed Add Record", Toast.LENGTH_SHORT).show()
            }
        }


    }


}