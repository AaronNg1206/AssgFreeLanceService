package com.nghycp.assgfreelanceservice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nghycp.assgfreelanceservice.databinding.RecentAddLayoutBinding
import com.nghycp.assgfreelanceservice.model.ModelJob


class RecentAddJobAdapter : RecyclerView.Adapter<RecentAddJobAdapter.HolderJob> {
    private val context: Context

    public var jobArrayList: ArrayList<ModelJob>
    private lateinit var binding: RecentAddLayoutBinding
    //constructor
    constructor(context: Context, jobArrayList: ArrayList<ModelJob>) {
        this.context = context
        this.jobArrayList = jobArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderJob {
        //inflate bind row_job.xml
        binding = RecentAddLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderJob(binding.root)
    }
        inner class HolderJob(itemView: View): RecyclerView.ViewHolder(itemView){
        var State: TextView = binding.RecentAddState
        var Salary: TextView = binding.RecentAddSalary
        var title: TextView = binding.RecentAddTitle
        var category: TextView = binding.RecentAddCategory
        var description: TextView = binding.RecentAddDescription

    }

    override fun onBindViewHolder(holder: HolderJob, position: Int) {
        //get data
        val model = jobArrayList[position]
        //val id = model.id
        val title = model.title
        val category = model.category
        val Description = model.Description
        //val Address = model.Address
        val State = model.State
        val Salary = model.Salary
        //val uid = model.uid

        //set data
        holder.State.text = State
        holder.Salary.text = Salary
        holder.title.text = title
        holder.category.text = category
        holder.description.text = Description
    }

    override fun getItemCount(): Int {
        return jobArrayList.size
    }

}