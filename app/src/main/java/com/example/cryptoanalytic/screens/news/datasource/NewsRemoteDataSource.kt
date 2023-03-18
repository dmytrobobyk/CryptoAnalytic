package com.example.cryptoanalytic.screens.news.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.cryptoanalytic.screens.news.api.NewsApi
import com.example.cryptoanalytic.common.utils.feedResponse.RssFeed
import com.example.database.wrapper.Result
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val api: NewsApi) : BaseDataSource() {

    suspend fun getNews(): Result<RssFeed> {
        return getResponse(request = { api.getRssNews() })
    }
}