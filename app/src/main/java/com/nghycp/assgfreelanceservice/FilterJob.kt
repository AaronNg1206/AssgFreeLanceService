package com.nghycp.assgfreelanceservice

import android.widget.Filter
import com.nghycp.assgfreelanceservice.model.ModelJob

class FilterJob: Filter {

    private var filterList: ArrayList<ModelJob>

    private var adapterJob: AdapterJob

    //constructor
    constructor(filterList: ArrayList<ModelJob>,adapterJob: AdapterJob):super(){
        this.filterList = filterList
        this.adapterJob = adapterJob
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty
        if(constraint != null && constraint.isNotEmpty()) {
            //searched value is nor null not empty
            //change to upper case or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels: ArrayList<ModelJob> = ArrayList()
            for (i in 0 until filterList.size) {
                if (filterList[i].title.uppercase().contains(constraint)) {
                    //add to filter list
                    filteredModels.add(filterList[i])
                }
                results.count = filteredModels.size
                results.values = filteredModels
            }
        }
            else{
                //search value is either null or empty

                results.count = filterList.size
                results.values = filterList
        }
        return results
    }

    override fun publishResults(constaint: CharSequence?, results: FilterResults) {
        //apply filter changes
        adapterJob.jobArrayList = results.values as ArrayList<ModelJob>

        //notify changes
        adapterJob.notifyDataSetChanged()
    }

}