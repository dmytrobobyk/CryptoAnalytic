package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbRoi(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val currency: String,
    val percentage: Double,
    val times: Double
)