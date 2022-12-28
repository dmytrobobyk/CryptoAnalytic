package com.example.cryptoanalytic.screens.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.database.embeeded.Cryptocurrency
import kotlinx.coroutines.flow.Flow

interface CryptocurrenciesRepository {
    suspend fun getLatestCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun saveFavoriteCryptocurrencyState(cryptocurrency: Cryptocurrency): Flow<Result<Any>>
}