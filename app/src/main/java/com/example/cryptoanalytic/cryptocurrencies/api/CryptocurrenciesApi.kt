package com.example.cryptoanalytic.cryptocurrencies.api

import com.example.cryptoanalytic.common.responses.base.BaseResponse
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import retrofit2.Response
import retrofit2.http.GET

interface CryptocurrenciesApi {

    @GET("cryptocurrency/listings/latest")
    suspend fun getLatestCryptocurrencies(): Response<BaseResponse<List<LatestResponse>>>

}