package com.example.cryptoanalytic.data.datasource.abs

import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyResponseItem
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPrices
import com.example.database.wrapper.Result

interface CryptocurrenciesRemoteDataSource {
    suspend fun getLatestCryptocurrencies(): Result<List<CryptocurrencyResponseItem>>
    suspend fun getCryptocurrencyInfo(id: String): Result<CryptocurrencyDetailsResponse>
    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Result<CryptocurrencyHistoryPrices>
    suspend fun getCryptocurrency(cryptocurrencyId: String): Result<List<CryptocurrencyResponseItem>>
}