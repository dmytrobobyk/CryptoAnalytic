package com.example.cryptoanalytic.screens.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency
import kotlinx.coroutines.flow.Flow

interface CryptocurrenciesRepository {
//    suspend fun getLatestCryptocurrencies(): Flow<Result<List<CryptocurrencyResponseItem>>>
    suspend fun getLatestCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
}