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
import com.nghycp.assgfreelanceservice.databinding.ActivityJobAppliedBinding
import com.nghycp.assgfreelanceservice.databinding.ActivityJobApplyLayoutBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobApplyadapter: RecyclerView.Adapter<JobApplyadapter.HolderJob>{
    private val context: Context
    var jobArrayList: ArrayList<ModelJob>
    var progress = "In Progress"

    private lateinit var binding: ActivityJobApplyLayoutBinding


    constructor(context: Context, jobArrayList: ArrayList<ModelJob>) {
        this.context = context
        this.jobArrayList = jobArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderJob {
        //inflate bind row_job.xml
        binding = ActivityJobApplyLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderJob(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return jobArrayList.size
    }
    inner class HolderJob(itemView: View): RecyclerView.ViewHolder(itemView){
        var title : TextView = binding.JobApplyTitle
        var category : TextView = binding.JobApplyCategory
        var Description : TextView = binding.JobApplyDescription
        var Salary : TextView = binding.JobApplySalary
        var state : TextView = binding.JobApplyState


        var completebtn: Button = binding.btnComplete

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

        holder.completebtn.setOnClickListener {
            progress = "Completed"
        }
    }

}