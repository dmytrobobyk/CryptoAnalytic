package com.example.cryptoanalytic.common.responses.latest

import com.google.gson.annotations.SerializedName

data class Platform(
    @SerializedName("")
    val id: Int,
    @SerializedName("")
    val name: String,
    @SerializedName("")
    val slug: String,
    @SerializedName("")
    val symbol: String,
    @SerializedName("")
    val token_address: String
)