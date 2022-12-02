package com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response

import com.google.gson.annotations.SerializedName

data class CryptocurrencyHistoryPrices(
    @SerializedName("market_caps") val marketCaps: List<List<Double>>,
    @SerializedName("prices") val prices: List<List<Double>>,
    @SerializedName("total_volumes") val totalVolumes: List<List<Double>>
)