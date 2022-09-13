package com.nghycp.assgfreelanceservice

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization.TITLE
import android.provider.MediaStore
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.nghycp.assgfreelanceservice.databinding.ActivityEditUserProfileBinding
import java.lang.Exception

class EditUserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserProfileBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var imageUri: Uri? = null

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        loadUserInfo()

        binding.doneEdit.setOnClickListener {
            validateData()
        }

        binding.editBack.setOnClickListener {
            onBackPressed()
        }

        binding.imageEdit.setOnClickListener {
            showImageAttachMenu()
        }

    }

    private var name = ""
    private var phoneNum = ""

    private fun validateData() {
        name = binding.editName.text.toString().trim()
        phoneNum = binding.editPhone.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
        } else if (phoneNum.isEmpty()) {
            Toast.makeText(this, "Please enter phone Number", Toast.LENGTH_SHORT).show()
        } else {
            if (imageUri == null) {
                updateProfile("")
            } else {
                uploadImage()
            }
        }

    }

    private fun uploadImage() {
        progressDialog.setMessage("Uploading Profile image")
        progressDialog.show()

        val filePathAndName = "ProfileImages/" + firebaseAuth.uid

        val reference = FirebaseStorage.getInstance().getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { takeSnapshot->
                val uriTask: Task<Uri> = takeSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"

                updateProfile(uploadedImageUrl)

            }
            .addOnFailureListener { e->

                progressDialog.dismiss()
                Toast.makeText(this,"Failed to upload image due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun updateProfile(uploadedImageUrl: String) {
        progressDialog.setMessage("Updating profile...")

        val hashMap: HashMap<String, Any> = HashMap()
        hashMap["name"] = "$name"
        hashMap["phoneNum"] = "$phoneNum"

        if (imageUri != null){
            hashMap["profileImage"] = uploadedImageUrl
        }

        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)

            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Profile updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to update profile",Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {

        val ref =
            Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = "${snapshot.child("name").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val phoneNum = "${snapshot.child("phoneNum").value}"

                    binding.editName.setText(name)
                    binding.editPhone.setText(phoneNum)

                    try {
                        Glide.with(this@EditUserProfile)
                            .load(profileImage)
                            .placeholder(R.drawable.user)
                            .into(binding.imageEdit)
                    } catch (e: Exception) {

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

    private fun showImageAttachMenu() {
        val popupMenu = PopupMenu(this, binding.imageEdit)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == 0) {
                pickImageCamera()
            } else if (id == 1) {
                pickImageGallery()

            }
            true
        }

    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private fun pickImageCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                binding.imageEdit.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data

                binding.imageEdit.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

}