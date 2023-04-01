package com.example.cryptoanalytic.data.remote.api.response.cryptocurrency

import com.google.gson.annotations.SerializedName

data class RoiResponse(
    @SerializedName("currency") val currency: String,
    @SerializedName("percentage") val percentage: Double,
    @SerializedName("times") val times: Double
)