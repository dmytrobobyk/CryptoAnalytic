package com.cryptoanalytic.data.repository

import com.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.cryptoanalytic.domain.entity.News
import com.cryptoanalytic.domain.repository.NewsRepository
import com.cryptoanalytic.domain.wrapper.Result
import com.cryptoanalytic.utils.mapToDbData
import com.cryptoanalytic.utils.mapToDomain
import com.example.database.DaoAggregator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val remoteDataSource: NewsRemoteDataSource, private val daoAggregator: DaoAggregator) : NewsRepository {

    override suspend fun getNews(): Flow<Result<List<News>>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getNews()
            if (result is Result.Success) {
                result.data?.channel?.items?.map {
                    it.mapToDbData()
                }?.let {
                    daoAggregator.saveRssCryptocurrencyNews(it).collect()
                }
            }
            daoAggregator.getRssCryptocurrencyNews().collect {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.map { it.mapToDomain() }))
                }
            }
            emit(Result.Finish)
        }
    }
}