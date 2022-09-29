package com.nghycp.assgfreelanceservice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.app.AlertDialog
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.ActivityJobShowLayoutBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobShowAdapter: RecyclerView.Adapter<JobShowAdapter.HolderJob>{
    private val context: Context
    var jobArrayList: ArrayList<ModelJob>

    private lateinit var binding: ActivityJobShowLayoutBinding



    //constructor
    constructor(context: Context, jobArrayList: ArrayList<ModelJob>) {
        this.context = context
        this.jobArrayList = jobArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderJob {
        //inflate bind row_job.xml
        binding = ActivityJobShowLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderJob(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return jobArrayList.size
    }
    inner class HolderJob(itemView: View): RecyclerView.ViewHolder(itemView){
        var title : TextView = binding.JobShowTitle
        var category : TextView = binding.jobShowJobCategory
        var Description : TextView = binding.textViewJobDescription
        var Salary : TextView = binding.jobShowPrice
        var state : TextView = binding.jobShowState

        var applybtn: Button = binding.btnApply

    }

    override fun onBindViewHolder(holder: HolderJob, position: Int) {
        //get data
        val model = jobArrayList[position]
        val id = model.id
        val title = model.title
        val category = model.category
        val Description = model.Description
        val Address = model.Address
        val State = model.State
        val Salary = model.Salary
        val uid = model.uid

        //set data
        holder.title.text = title
        holder.category.text= category
        holder.Salary.text = Salary
        holder.Description.text = Description
        holder.state.text = State

        holder.applybtn.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Apply Job")
                .setMessage("Are u confirm to apply this job?").setPositiveButton("confirm"){a,d->
                    Toast.makeText(context,"Apply...", Toast.LENGTH_SHORT).show()
                    applyJob(model)
                } .setNegativeButton("Cancel"){a,d->
                    a.dismiss()
                }.show()
        }
    }

    private fun applyJob(model: ModelJob) {
        //get id of job to apply


        val title = model.title
        val category = model.category
        val description = model.Description
        val address = model.Address
        val state = model.State
        val salary = model.Salary
        val uid = model.uid
        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["title"] = title
        hashMap["category"] = category
        hashMap["Description"] = description
        hashMap["Address"] = address
        hashMap["State"] = state
        hashMap["Salary"] = salary
        hashMap["uid"] = uid

        val ref = Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("JobApply")

        //if (uid .equals(ref(uid))){
        //   Toast.makeText(context, "Job Already taken!choose another job", Toast.LENGTH_SHORT).show()

        //}else{

            ref.child("$timestamp")
                .setValue(hashMap)
                .addOnSuccessListener {
                    Toast.makeText(context,"Successful apply",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {e->
                    Toast.makeText(context,"unable to apply due to ${e.message}",Toast.LENGTH_SHORT).show()
        //        }
        }


    }


}