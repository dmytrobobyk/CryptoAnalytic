package com.example.cryptoanalytic.common.responses.latest

import com.example.cryptoanalytic.common.responses.base.currencyResponses.CurrencyItemResponse
import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("BTH")
    val BTC: CurrencyItemResponse,
    @SerializedName("ETH")
    val ETH: CurrencyItemResponse,
    @SerializedName("USD")
    val USD: CurrencyItemResponse
)