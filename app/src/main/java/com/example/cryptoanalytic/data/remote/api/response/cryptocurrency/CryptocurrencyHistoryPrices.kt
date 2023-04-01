package com.example.cryptoanalytic.data.remote.api.response.cryptocurrency

import com.google.gson.annotations.SerializedName

data class CryptocurrencyHistoryPrices(
    @SerializedName("market_caps") val marketCaps: List<List<Double>>,
    @SerializedName("prices") val prices: List<List<Double>>,
    @SerializedName("total_volumes") val totalVolumes: List<List<Double>>
)