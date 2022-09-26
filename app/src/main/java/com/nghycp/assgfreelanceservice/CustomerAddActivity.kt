package com.nghycp.assgfreelanceservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
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

            validateAddJob()
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

    private var title = ""
    private var category = ""
    private var description = ""
    private var dueDate = ""
    private var address = ""
    private var state = ""
    private var salary = ""

    private fun validateAddJob(){
        title = binding.editTextJobTitle.text.toString().trim()
        category = binding.spinnerJobCategory.toString().trim()
        description = binding.editTextJobDescription.text.toString().trim()
        dueDate = binding.editTextDate.text.toString().trim()
        address = binding.editTextAddress.text.toString().trim()
        state = binding.spinnerState.toString().trim()
        salary =binding.editTextNumberSalary.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(this, "Enter your title", Toast.LENGTH_SHORT).show()
        }
        else if (description.isEmpty()){
            Toast.makeText(this,"Enter description", Toast.LENGTH_SHORT).show()
        }
        else if (salary.isEmpty()){
            Toast.makeText(this,"Enter Salary", Toast.LENGTH_SHORT).show()
        }

        else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {

        /*      progressDialog.setMessage("Creating Account..")
              progressDialog.show()

              firebaseAuth.createUserWithEmailAndPassword(email, password)
                  .addOnSuccessListener {
                      updateUserInfo()
                  }
                  .addOnFailureListener { e->
                      progressDialog.dismiss()
                      Toast.makeText(this,"Failed to Register due to ${e.message}", Toast.LENGTH_SHORT).show()
                  }

          }*/

    }
}