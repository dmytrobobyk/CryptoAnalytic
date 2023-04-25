package com.cryptoanalytic.domain.entity

data class CryptocurrencyDetails(
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
    val symbol: String
)
