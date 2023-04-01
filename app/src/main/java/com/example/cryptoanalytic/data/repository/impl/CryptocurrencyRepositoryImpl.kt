package com.example.cryptoanalytic.data.repository.impl

import com.example.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPrices
import com.example.cryptoanalytic.utils.formatters.mapToIntermediateDataClass
import com.example.database.DaoAggregator
import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptocurrencyRepositoryImpl @Inject constructor(
    private val remoteDataSource: CryptocurrenciesRemoteDataSource,
    private val daoAggregator: DaoAggregator
) : CryptocurrencyRepository {
    override suspend fun getCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getLatestCryptocurrencies()
            if (result is Result.Success) {
                result.data?.let {
                    it.map { it.mapToIntermediateDataClass() }
                }?.let {
                    daoAggregator.saveCryptocurrencyList(it).collect()
                }
            }
            daoAggregator.getCryptocurrencyList().collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun saveFavoriteCryptocurrencyState(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.saveCryptocurrencyFavoriteState(cryptocurrencyId, state).collect()
            emit(Result.Finish)
        }
    }

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

    override suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getFavoriteCryptocurrencies().collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun removeCryptocurrencyFromFavorite(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.saveCryptocurrencyFavoriteState(cryptocurrencyId, state).collect()
            emit(Result.Finish)
        }
    }
}