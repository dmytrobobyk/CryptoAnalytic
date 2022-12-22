package com.example.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.database.DB_CRYPTOCURRENCY_DETAILS

@Entity(tableName = DB_CRYPTOCURRENCY_DETAILS)
data class DbCryptocurrencyDetails(
    @PrimaryKey(autoGenerate = false)
    val cryptocurrencyDetailsId: String,
    val categories: List<String>,
    val coingeckoRank: Int,
    val coingeckoScore: Double,
    val description: String,
    val genesisDate: String,
    val hashingAlgorithm: String,
    val lastUpdated: String,
    val marketCap_rank: Int,
    val name: String,
    val sentimentVotesDownPercentage: Double,
    val sentimentVotesUpPercentage: Double,
    val symbol: String,
    @Ignore
    @Embedded
    val marketData: DbMarketData,
    @Ignore
    @Embedded
    val links: DbLinks
    )