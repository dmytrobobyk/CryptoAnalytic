package com.example.cryptoanalytic.data.repository.abs

import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPrices
import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyRepository {
    suspend fun getCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun saveFavoriteCryptocurrencyState(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>>
    suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>>
    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Flow<Result<CryptocurrencyHistoryPrices>>
    suspend fun getCryptocurrencyMarketData(cryptocurrencyId: String): Flow<Result<Cryptocurrency>>
    suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun removeCryptocurrencyFromFavorite(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>>
}