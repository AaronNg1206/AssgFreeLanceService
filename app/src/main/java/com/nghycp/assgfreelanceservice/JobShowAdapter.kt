package com.nghycp.assgfreelanceservice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nghycp.assgfreelanceservice.databinding.ActivityJobShowLayoutBinding
import com.nghycp.assgfreelanceservice.model.ModelJob

class JobShowAdapter : RecyclerView.Adapter<JobShowAdapter.HolderJob>{
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
        var Description : TextView = binding.jobShowDescription
        var Salary : TextView = binding.jobShowPrice
        var state : TextView = binding.jobShowState

       //var deleteBtn: ImageButton = binding.deleteBtn
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
    }
}