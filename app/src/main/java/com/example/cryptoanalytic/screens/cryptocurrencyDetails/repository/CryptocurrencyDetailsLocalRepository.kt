package com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository

import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource.CryptocurrencyDetailsDataSource
import com.example.cryptoanalytic.utils.formatters.mapToIntermediateDataClass
import com.example.database.DaoAggregator
import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptocurrencyDetailsLocalRepository @Inject constructor(
    private val remoteDataSource: CryptocurrencyDetailsDataSource,
    private val daoAggregator: DaoAggregator
) : CryptocurrencyDetailsRepository {

    override suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>> {
        return flow {
            emit(Result.Loading)
            emit(remoteDataSource.getCryptocurrencyInfo(id))
            emit(Result.Finish)
        }
    }

    override suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Flow<Result<CryptocurrencyHistoryPrices>> {
        return flow {
            emit(Result.Loading)
            emit(remoteDataSource.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo))
            emit(Result.Finish)
        }
    }

    override suspend fun getCryptocurrencyMarketData(cryptocurrencyId: String): Flow<Result<Cryptocurrency>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getCryptocurrency(cryptocurrencyId)
            if (result is Result.Success) {
                result.data?.first()?.mapToIntermediateDataClass()?.let {
                    daoAggregator.saveCryptocurrency(it)
                    daoAggregator.getCryptocurrency(it.dbCryptocurrency.id).collect { emit(it) }
                }
            }

            emit(Result.Finish)
        }
    }
}