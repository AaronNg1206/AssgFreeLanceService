package com.nghycp.assgfreelanceservice.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nghycp.assgfreelanceservice.model.ModelJob

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job: ModelJob)

    @Delete
    suspend fun delete(job: ModelJob)

    @Update
    suspend fun update(job: ModelJob)

  @Query("SELECT * FROM Job")
    fun getAll(): LiveData<List<ModelJob>>
/*
   @Query("SELECT * FROM Job WHERE Title = :title")
    fun findByTitle(title: String) : ModelJob*/

}