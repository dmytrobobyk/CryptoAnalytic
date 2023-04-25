package com.cryptoanalytic.data.repository

import com.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.cryptoanalytic.domain.entity.Cryptocurrency
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyDetailsResponse
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyHistoryPrices
import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import com.cryptoanalytic.domain.wrapper.Result
import com.cryptoanalytic.utils.mapToDbData
import com.cryptoanalytic.utils.mapToDomain
import com.example.database.DaoAggregator
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
                    it.map { it.mapToDomain() }
                }?.let {
                    daoAggregator.saveCryptocurrencyList(it.map { item -> item.mapToDbData() }).collect()
                }
            }
            daoAggregator.getCryptocurrencyList().collect {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.map { it.mapToDomain() }))
                }
            }
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
            remoteDataSource.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo).let {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.mapToDomain()))
                }
            }
            emit(Result.Finish)
        }
    }

    override suspend fun getCryptocurrencyMarketData(cryptocurrencyId: String): Flow<Result<Cryptocurrency>> {
        return flow {
            emit(Result.Loading)

            val result = remoteDataSource.getCryptocurrency(cryptocurrencyId)
            if (result is Result.Success) {
                result.data?.first()?.mapToDomain()?.let {
                    daoAggregator.saveCryptocurrency(it.mapToDbData())
                    daoAggregator.getCryptocurrency(it.id).collect {
                        if (it is Result.Success) {
                            it.data?.let { emit(Result.Success(it.mapToDomain())) }
                        }
                    }
                }
            }

            emit(Result.Finish)
        }
    }

    override suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getFavoriteCryptocurrencies().collect {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.map { it.mapToDomain() }))
                }
            }
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