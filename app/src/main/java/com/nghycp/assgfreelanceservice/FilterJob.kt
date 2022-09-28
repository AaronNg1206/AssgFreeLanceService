package com.nghycp.assgfreelanceservice

import android.widget.Filter

class FilterJob: Filter {

    private var filterList: ArrayList<ModelJob>
    private var adapterJob: AdapterJob

    constructor(filterList: ArrayList<ModelJob>, adapterJob: AdapterJob):super(){
        this.filterList = filterList
        this.adapterJob = adapterJob
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        if (constraint != null && constraint.isNotEmpty()){
            //change to upper case or lower case to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels:ArrayList<ModelJob> = ArrayList()
            for (i in 0 until filterList.size){
                //validate
                if(filterList[i].category.uppercase().contains(constraint)){
                    //add to filter list
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            //search value is empty or null
            results.count = filterList.size
            results.values = filterList
        }

        return results

    }

    override fun publishResults(p0: CharSequence?, results: FilterResults) {
        adapterJob.jobArrayList = results.values as ArrayList<ModelJob>

        //notify changes
        adapterJob.notifyDataSetChanged()
    }

}