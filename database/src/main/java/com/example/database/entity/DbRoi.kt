package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.database.DB_ROI

@Entity(
    tableName = DB_ROI, foreignKeys = [
        ForeignKey(
            entity = DbCryptocurrency::class,
            parentColumns = ["id"],
            childColumns = ["cryptocurrencyId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DbRoi(
    @PrimaryKey(autoGenerate = false)
    val cryptocurrencyId: String,
    val currency: String,
    val percentage: Double,
    val times: Double
)