package com.example.cryptoanalytic.screens.news.repository

import com.example.database.entity.DbNews
import kotlinx.coroutines.flow.Flow
import com.example.database.wrapper.Result

interface NewsRepository {
    suspend fun getNews(): Flow<Result<List<DbNews>>>
}