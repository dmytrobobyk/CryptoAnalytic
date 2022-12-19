package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation

@Entity
data class DbMarketData (
    @Relation(parentColumn = "id", entityColumn = "cryptocurrencyId")
    val cryptocurrencyId: String,
    val currentPrice: Double, // set price only in USD
    val marketCap: Double,
    val maxSupply: Double,
    val totalSupply: Double,
    val priceChangePercentage_14d_in_currency: Double,
    val priceChangePercentage_1h_in_currency: Double,
    val priceChangePercentage_1y: Double,
    val priceChangePercentage_1y_in_currency: Double,
    val priceChangePercentage_200d: Double,
    val priceChangePercentage_200d_in_currency: Double,
    val priceChangePercentage_24h: Double,
    val priceChangePercentage_24h_in_currency: Double,
    val priceChangePercentage_30d: Double,
    val priceChangePercentage_30d_in_currency: Double,
    val priceChangePercentage_60d: Double,
    val priceChangePercentage_60d_in_currency: Double,
    val priceChangePercentage_7d: Double,
    val priceChangePercentage_7d_in_currency: Double,
)