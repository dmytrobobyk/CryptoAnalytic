package com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository

import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource.CryptocurrencyDetailsDataSource
import kotlinx.coroutines.flow.flow
import com.example.cryptoanalytic.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptocurrencyDetailsLocalRepository @Inject constructor(private val dataSource: CryptocurrencyDetailsDataSource): CryptocurrencyDetailsRepository {

    override suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getCryptocurrencyInfo(id))
            emit(Result.Finish)
        }
    }
}