package com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository

import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource.CryptocurrencyDetailsDataSource
import kotlinx.coroutines.flow.flow
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
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

    override suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Flow<Result<CryptocurrencyHistoryPrices>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo))
            emit(Result.Finish)
        }
    }
}