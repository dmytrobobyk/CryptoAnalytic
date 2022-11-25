package com.example.cryptoanalytic.screens.cryptocurrencyDetails.api

import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrencyDetailsApi {

    @GET("coins/{id}")
    suspend fun getCryptocurrencyInfo(@Query("id") id: String): Response<CryptocurrencyDetailsResponse>
}