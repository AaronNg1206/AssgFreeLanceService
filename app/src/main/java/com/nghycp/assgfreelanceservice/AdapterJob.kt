package com.nghycp.assgfreelanceservice

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.assgfreelanceservice.databinding.RowJobBinding

class AdapterJob : RecyclerView.Adapter<AdapterJob.HolderJob>,Filterable {

    private val context: Context
    public var jobArrayList: ArrayList<ModelJob>
    private var filterList: ArrayList<ModelJob>

    private var filter: FilterJob? = null

    private lateinit var binding: RowJobBinding

    constructor(context: Context, jobArrayList: ArrayList<ModelJob>) {
        this.context = context
        this.jobArrayList = jobArrayList
        this.filterList = jobArrayList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderJob {
        //bind row_job.xml
        binding = RowJobBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderJob(binding.root)
    }

    override fun onBindViewHolder(holder: HolderJob, position: Int) {
        //get data, set data handle click

        //get data
        val model = jobArrayList[position]
        val title = model.title
        val category = model.category
        val Description = model.Description
        val Address = model.Address
        val State = model.State
        val Salary = model.Salary
        val uid = model.uid

        //set data
        holder.jobTv.text = title

        //handle click,delete job
        holder.deleteBtn.setOnClickListener {
            //confirm button
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are u sure want to delete this job?")
                .setPositiveButton("Confirm") { a, d ->
                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                    deleteJob(model, holder)
                }
                .setNegativeButton("Cancel") { a, d ->
                    a.dismiss()
                }
                .show()
        }


    }

    private fun deleteJob(model: ModelJob, holder: HolderJob) {
        //get the job to delete
        val title = model.title

        //firebase
        val ref =
            Firebase.database("https://freelanceservice-48fbf-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Job")
        ref.child(title)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Unable to delete due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun getItemCount(): Int {
        //number of item in list
        return jobArrayList.size
    }

    //ViewHolder class to hold /init UI views for row_job.xml
    inner class HolderJob(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var jobTv: TextView = binding.jobTv
        var deleteBtn: ImageButton = binding.deleteBtn
    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter = FilterJob(filterList,this)
        }
        return filter as FilterJob
    }

}