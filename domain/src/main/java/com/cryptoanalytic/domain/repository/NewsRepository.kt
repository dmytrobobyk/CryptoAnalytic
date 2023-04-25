package com.cryptoanalytic.domain.repository

import com.cryptoanalytic.domain.entity.News
import com.cryptoanalytic.domain.wrapper.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<Result<List<News>>>
}