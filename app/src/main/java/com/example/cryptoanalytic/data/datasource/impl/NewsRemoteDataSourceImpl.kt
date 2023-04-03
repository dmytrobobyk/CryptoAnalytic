package com.example.cryptoanalytic.data.datasource.impl

import com.example.cryptoanalytic.common.utils.getResponse
import com.example.cryptoanalytic.data.remote.api.response.news.RssFeed
import com.example.cryptoanalytic.data.remote.api.NewsService
import com.example.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.example.cryptoanalytic.data.di.qualifiers.RetrofitRss
import com.example.database.wrapper.Result
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(@RetrofitRss private val api: NewsService) : NewsRemoteDataSource {

    override suspend fun getNews(): Result<RssFeed> {
        return getResponse(request = { api.getRssNews() })
    }
}