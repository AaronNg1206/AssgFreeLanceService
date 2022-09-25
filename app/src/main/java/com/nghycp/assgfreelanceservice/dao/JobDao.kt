package com.nghycp.assgfreelanceservice.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nghycp.assgfreelanceservice.model.Job

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job: Job)

    @Delete
    suspend fun delete(job: Job)

    @Update
    suspend fun update(job: Job)

    @Query("SELECT * FROM job")
    fun getAll(): LiveData<List<Job>>

/*    @Query("SELECT * FROM Job WHERE title = :title")
    fun findByTitle(Title: String) : Job

    @Query("SELECT * FROM contact WHERE name LIKE :name")
    fun findByName(name: String): Contact*/
}