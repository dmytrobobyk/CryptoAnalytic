package com.example.database

import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.*

class DaoAggregator(private val database: AppDatabase) {

    // Cryptocurrency list
    suspend fun getCryptocurrencyList(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            database.cryptocurrencyDao().getAll().collect {
                emit(Result.Success(it))
            }
        }
    }

    suspend fun saveCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(cryptocurrencyList.forEach { cryptocurrency ->
                database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency)
                cryptocurrency.dbRoi?.let { it -> database.roiDao().insert(it) }
            }))
        }
    }

    suspend fun deleteCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(cryptocurrencyList.forEach {
                database.cryptocurrencyDao().delete(it.dbCryptocurrency)
                it.dbRoi?.let { it1 -> database.roiDao().delete(it1) }
            }))
        }
    }

    suspend fun getFavoriteCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow{
            database.cryptocurrencyDao().getFavorites().collect { emit(Result.Success(it)) }
        }
    }

    suspend fun saveCryptocurrencyFavoriteState(cryptocurrency: Cryptocurrency): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(database.cryptocurrencyDao().update(cryptocurrency.dbCryptocurrency)))
        }
    }




    // Cryptocurrency details


}