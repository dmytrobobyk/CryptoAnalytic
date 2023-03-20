package com.example.cryptoanalytic.screens.news.repository

import android.text.Html
import com.example.cryptoanalytic.screens.news.datasource.NewsRemoteDataSource
import com.example.cryptoanalytic.utils.formatters.DateTimeUtil.formatPublicationDate
import com.example.database.DaoAggregator
import com.example.database.entity.DbNews
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsLocalRepository @Inject constructor(private val remoteDataSource: NewsRemoteDataSource, private val daoAggregator: DaoAggregator) :
    NewsRepository {
    override suspend fun getNews(): Flow<Result<List<DbNews>>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getNews()
            if (result is Result.Success) {
                result.data?.channel?.items?.map { rssItem ->
                    with(rssItem) {
                        DbNews(title = title, link = link, imageUrl = imageUrl, publicationDate = formatPublicationDate(pubDate), description = Html.fromHtml(description).split("\n")[2])
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

