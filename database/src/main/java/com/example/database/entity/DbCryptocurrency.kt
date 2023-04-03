package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DB_CRYPTOCURRENCY

@Entity(tableName = DB_CRYPTOCURRENCY)
data class DbCryptocurrency(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val ath: Double,
    val athChangePercentage: Double,
    val athDate: String,
    val atl: Double,
    val atlChangePercentage: Double,
    val atlDate: String,
    val circulatingSupply: Double,
    val currentPrice: Double,
    val fullyDilutedValuation: Long,
    val high24h: Double,
    val image: String,
    val lastUpdated: String,
    val low24h: Double,
    val marketCap: Long,
    val marketCapChange24h: Double,
    val marketCapChangePercentage24h: Double,
    val marketCapRank: Int,
    val maxSupply: Double,
    val name: String,
    val priceChange24h: Double,
    val priceChangePercentage24h: Double,
    val symbol: String,
    val totalSupply: Double,
    val totalVolume: Double,
    var isFavorite: Boolean = false
)