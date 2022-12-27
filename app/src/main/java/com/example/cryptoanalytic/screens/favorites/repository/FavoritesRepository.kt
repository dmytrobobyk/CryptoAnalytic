package com.example.cryptoanalytic.screens.favorites.repository

import com.example.cryptoanalytic.common.Result
import com.example.database.embeeded.Cryptocurrency
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun removeCryptocurrencyFromFavorite(cryptocurrency: Cryptocurrency): Flow<Result<Any>>
}