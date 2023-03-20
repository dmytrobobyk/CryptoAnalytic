package com.example.cryptoanalytic.screens.favorites.repository

import com.example.database.DaoAggregator
import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class FavoritesLocalRepository(private val daoAggregator: DaoAggregator): FavoritesRepository {

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