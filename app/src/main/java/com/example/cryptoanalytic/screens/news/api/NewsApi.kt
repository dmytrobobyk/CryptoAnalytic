package com.example.cryptoanalytic.screens.news.api

import com.example.cryptoanalytic.common.utils.feedResponse.RssFeed
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("tag/regulation")
    suspend fun getRssNews(): Response<RssFeed>
}