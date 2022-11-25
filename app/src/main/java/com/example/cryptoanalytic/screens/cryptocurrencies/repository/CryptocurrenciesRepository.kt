package com.example.cryptoanalytic.screens.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem
import kotlinx.coroutines.flow.Flow

interface CryptocurrenciesRepository {
    //CoinMarketCap API
//    suspend fun getLatestCryptocurrencies(): Flow<Result<List<LatestResponse>>>
    //CoinGecko API
    suspend fun getLatestCryptocurrencies(): Flow<Result<List<CoinMarketResponseItem>>>
}