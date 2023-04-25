package com.cryptoanalytic.domain.entity.cryptocurrencyDetails


data class CryptocurrencyHistoryPrices(
    val marketCaps: List<List<Double>>,
    val prices: List<List<Double>>,
    val totalVolumes: List<List<Double>>
)