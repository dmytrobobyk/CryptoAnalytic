package com.example.cryptoanalytic.screens.favorites.repository

import com.example.cryptoanalytic.common.Result
import com.example.database.DaoAggregator
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoritesLocalRepository(private val daoAggregator: DaoAggregator): FavoritesRepository {

    override suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(daoAggregator.getFavoriteCryptocurrencies()))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeCryptocurrencyFromFavorite(cryptocurrency: Cryptocurrency): Flow<Result<Any>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(daoAggregator.saveCryptocurrencyFavoriteState(cryptocurrency)))
            emit(Result.Finish)
        }.flowOn(Dispatchers.IO)
    }
}