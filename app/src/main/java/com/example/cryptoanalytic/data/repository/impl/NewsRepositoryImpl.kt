package com.example.cryptoanalytic.data.repository.impl

import android.text.Html
import com.example.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.example.cryptoanalytic.data.repository.abs.NewsRepository
import com.example.cryptoanalytic.utils.formatters.DateTimeUtil
import com.example.database.DaoAggregator
import com.example.database.entity.DbNews
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val remoteDataSource: NewsRemoteDataSource, private val daoAggregator: DaoAggregator) : NewsRepository {

    override suspend fun getNews(): Flow<Result<List<DbNews>>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getNews()
            if (result is Result.Success) {
                result.data?.channel?.items?.map { rssItem ->
                    with(rssItem) {
                        DbNews(title = title, link = link, imageUrl = imageUrl, publicationDate = DateTimeUtil.formatPublicationDate(pubDate), description = Html.fromHtml(description).split("\n")[2])
                    }
                }?.let {
                    daoAggregator.saveRssCryptocurrencyNews(it).collect()
                }
            }
            daoAggregator.getRssCryptocurrencyNews().collect { emit(it) }
            emit(Result.Finish)
        }
    }
}