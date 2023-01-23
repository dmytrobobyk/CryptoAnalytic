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
) {
    fun getAllParamsInMultilineString(): String {
        var result = ""
        when {
            lessThan != 0.0 -> result + "less than $lessThan\n"
            moreThan != 0.0 -> result + "more than $moreThan\n"
            increasedBy != 0.0 -> result + "increased by $increasedBy\n"
            decreasedBy != 0.0 -> result + "decreased by $decreasedBy\n"
            changedBy != 0.0 -> result + "changed by $changedBy"
        }
        return result
    }
}