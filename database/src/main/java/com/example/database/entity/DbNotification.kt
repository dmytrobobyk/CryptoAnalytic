package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DB_NOTIFICATION

@Entity(tableName = DB_NOTIFICATION)
data class DbNotification(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Int,
    val cryptocurrencyId: String,
    val cryptocurrencyShortName: String,
    val cryptocurrencyName: String,
    val cryptocurrencyMarketRank: Int,
    val cryptocurrencyImageUrl: String,
    val lessThan: Double,
    val moreThan: Double,
    val increasedBy: Double,
    val decreasedBy: Double,
    val changedBy: Double,
    val alertFrequency: Long,
    val isPersistent: Boolean = false,
    val notes: String
)