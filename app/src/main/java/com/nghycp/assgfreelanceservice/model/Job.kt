package com.nghycp.assgfreelanceservice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Job(
    @PrimaryKey val jobID :String,
    val title: String,
    val category: String,
    val description: String,
    val address: String,
    val salary: String,
    val state: String,
    val date: Date,
)

