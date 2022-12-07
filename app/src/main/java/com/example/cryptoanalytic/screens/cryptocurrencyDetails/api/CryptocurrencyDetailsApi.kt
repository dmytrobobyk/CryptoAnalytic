package com.example.cryptoanalytic.screens.cryptocurrencyDetails.api

import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptocurrencyDetailsApi {

    @GET("coins/{id}")
    suspend fun getCryptocurrencyInfo(@Path("id") id: String): Response<CryptocurrencyDetailsResponse>

    @GET("coins/{currency}/market_chart/range")
    suspend fun getHistoryOfPriceForDateRange(
        @Path("currency") currencySymbol: String, //e.g. bitcoin
        @Query("from") unixTimeFrom: Long, //e.g. 1654370519 - 04.06.2022 192:21:59
        @Query("to") unixTimeTo: Long, //e.g. 1656962519 - 04.07.2022 192:21:59
    ): Response<CryptocurrencyHistoryPrices>
}