package com.cryptoanalytic.domain.repository

import com.cryptoanalytic.domain.entity.Cryptocurrency
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyDetailsResponse
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyHistoryPrices
import kotlinx.coroutines.flow.Flow
import com.cryptoanalytic.domain.wrapper.Result

interface CryptocurrencyRepository {
    suspend fun getCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun saveFavoriteCryptocurrencyState(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>>
    suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>>
    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Flow<Result<CryptocurrencyHistoryPrices>>
    suspend fun getCryptocurrencyMarketData(cryptocurrencyId: String): Flow<Result<Cryptocurrency>>
    suspend fun getFavoritesCryptocurrencies(): Flow<Result<List<Cryptocurrency>>>
    suspend fun removeCryptocurrencyFromFavorite(cryptocurrencyId: String, state: Boolean): Flow<Result<Any>>
}