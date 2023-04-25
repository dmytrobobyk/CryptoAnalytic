package com.cryptoanalytic.data.datasource.impl

import com.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.cryptoanalytic.data.di.qualifiers.RetrofitRss
import com.cryptoanalytic.data.remote.api.NewsService
import com.cryptoanalytic.data.remote.api.response.news.RssFeed
import com.cryptoanalytic.utils.getResponse
import com.cryptoanalytic.domain.wrapper.Result
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(@RetrofitRss private val api: NewsService) : NewsRemoteDataSource {

    override suspend fun getNews(): Result<RssFeed> {
        return getResponse(request = { api.getRssNews() })
    }
}