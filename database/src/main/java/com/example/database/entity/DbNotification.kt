package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DB_NOTIFICATION

@Entity(tableName = DB_NOTIFICATION)
data class DbNotification(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Long = 0,
    val cryptocurrencyId: String,
    val cryptocurrencyShortName: String,
    val cryptocurrencyName: String,
    val cryptocurrencyPrice: Double,
    val cryptocurrencyMarketRank: Int,
    val cryptocurrencyImageUrl: String,
    val lessThan: Double,
    val moreThan: Double,
    val increasedBy: Double,
    val decreasedBy: Double,
    val changedBy: Double,
    val alertFrequency: Long,
    val isPersistent: Boolean = false,
    val isActive: Boolean = true,
    val notes: String
) {
    fun getAllParamsInMultilineString(): String {
        var result = ""
        if(lessThan != 0.0) {
            result += "less than $lessThan\n"
        }
        if (moreThan != 0.0) {
            result += "more than $moreThan\n"
        }
        if (increasedBy != 0.0) {
            result += "increased by $increasedBy\n"
        }
        if (decreasedBy != 0.0) {
            result += "decreased by $decreasedBy\n"
        }
        if (changedBy != 0.0) {
            result += "changed by $changedBy"
        }
        return result
    }
}