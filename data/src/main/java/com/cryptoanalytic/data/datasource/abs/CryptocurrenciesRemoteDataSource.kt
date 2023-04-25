package com.cryptoanalytic.data.datasource.abs

import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPricesResponse
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyDetailsResponse
import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyResponseItem
import com.cryptoanalytic.domain.wrapper.Result

interface CryptocurrenciesRemoteDataSource {
    suspend fun getLatestCryptocurrencies(): Result<List<CryptocurrencyResponseItem>>
    suspend fun getCryptocurrencyInfo(id: String): Result<CryptocurrencyDetailsResponse>
    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Result<CryptocurrencyHistoryPricesResponse>
    suspend fun getCryptocurrency(cryptocurrencyId: String): Result<List<CryptocurrencyResponseItem>>
}