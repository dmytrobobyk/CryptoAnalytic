package com.example.cryptoanalytic.screens.cryptocurrencies.api

import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptocurrenciesApi {

    //Coin market cap api
//    @GET("cryptocurrency/listings/latest")
//    suspend fun getLatestCryptocurrencies(): Response<BaseResponse<List<LatestResponse>>>


    //Coin gecko api
    @GET("coins/markets")
    suspend fun getLatestCryptocurrencies(@Query("vs_currency") currency: String = "USD"): Response<List<CoinMarketResponseItem>>

}