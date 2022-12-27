package com.example.cryptoanalytic.screens.cryptocurrencies.api

import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface CryptocurrenciesApi {

    @GET("coins/markets")
    suspend fun getLatestCryptocurrencies(): Response<List<CryptocurrencyResponseItem>>

}