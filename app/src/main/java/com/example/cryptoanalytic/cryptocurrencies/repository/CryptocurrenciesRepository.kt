package com.example.cryptoanalytic.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import kotlinx.coroutines.flow.Flow

interface CryptocurrenciesRepository {
    suspend fun getLatestCryptocurrencies(): Flow<Result<List<LatestResponse>>>
}