package com.example.cryptoanalytic.data.repository.abs

import com.example.database.entity.DbNews
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<Result<List<DbNews>>>
}