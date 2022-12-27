package com.example.cryptoanalytic.screens.cryptocurrencies.api.response

import com.google.gson.annotations.SerializedName

data class RoiResponse(
    @SerializedName("currency") val currency: String,
    @SerializedName("percentage") val percentage: Double,
    @SerializedName("times") val times: Double
)