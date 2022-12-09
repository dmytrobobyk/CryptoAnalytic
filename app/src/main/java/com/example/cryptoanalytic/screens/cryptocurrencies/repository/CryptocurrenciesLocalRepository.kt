package com.example.cryptoanalytic.screens.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import com.example.cryptoanalytic.screens.cryptocurrencies.datasource.CryptocurrenciesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptocurrenciesLocalRepository @Inject constructor(private val dataSource: CryptocurrenciesDataSource): CryptocurrenciesRepository {

    override suspend fun getLatestCryptocurrencies(): Flow<Result<List<CryptocurrencyResponseItem>>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getLatestCryptocurrencies())
            emit(Result.Finish)
        }
    }
}