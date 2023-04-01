package com.example.cryptoanalytic.data.remote.api

import com.example.cryptoanalytic.data.remote.api.response.news.RssFeed
import retrofit2.Response
import retrofit2.http.GET

interface NewsService {

    @GET("tag/regulation")
    suspend fun getRssNews(): Response<RssFeed>
}