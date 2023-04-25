package com.cryptoanalytic.data.datasource.abs

import com.cryptoanalytic.data.remote.api.response.news.RssFeed
import com.cryptoanalytic.domain.wrapper.Result

interface NewsRemoteDataSource {
    suspend fun getNews(): Result<RssFeed>
}