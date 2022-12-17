package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity
data class DbCryptocurrency(
    val id: String,
    val categories: List<String>,
    val coingecko_rank: Int,
    val coingecko_score: Double,
    val description: String,
    val genesis_date: String,
    val hashing_algorithm: String,
    val last_updated: String,
    val links: DbLinks,
    val market_cap_rank: Int,
    val market_data: DbMarketData,
    val name: String,
    val sentiment_votes_down_percentage: Double,
    val sentiment_votes_up_percentage: Double,
    val symbol: String,
)