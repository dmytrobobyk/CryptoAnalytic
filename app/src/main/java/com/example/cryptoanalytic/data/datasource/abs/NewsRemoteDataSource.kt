package com.example.cryptoanalytic.data.datasource.abs

import com.example.cryptoanalytic.data.remote.api.response.news.RssFeed
import com.example.database.wrapper.Result

interface NewsRemoteDataSource {
    suspend fun getNews(): Result<RssFeed>
}