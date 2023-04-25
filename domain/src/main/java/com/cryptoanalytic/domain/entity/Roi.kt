package com.cryptoanalytic.domain.entity

data class Roi(
    val cryptocurrencyId: String,
    val currency: String,
    val percentage: Double,
    val times: Double
)
