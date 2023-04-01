package com.example.cryptoanalytic.data.datasource.impl

import com.example.cryptoanalytic.common.utils.getResponse
import com.example.cryptoanalytic.data.remote.api.CryptocurrencyService
import com.example.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyResponseItem
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPrices
import com.example.database.wrapper.Result
import javax.inject.Inject

class CryptocurrenciesRemoteDataSourceImpl @Inject constructor(private val api: CryptocurrencyService) : CryptocurrenciesRemoteDataSource {

    override suspend fun getLatestCryptocurrencies(): Result<List<CryptocurrencyResponseItem>> {
        return getResponse(request = { api.getLatestCryptocurrencies() })
    }

    override suspend fun getCryptocurrencyInfo(id: String): Result<CryptocurrencyDetailsResponse> {
        return getResponse(request = { api.getCryptocurrencyInfo(id) })
    }

    override suspend fun getHistoryOfPriceForDateRange(
        currencySymbol: String,
        unixTimeFrom: Long,
        unixTimeTo: Long
    ): Result<CryptocurrencyHistoryPrices> {
        return getResponse(request = { api.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo) } )
    }

    override suspend fun getCryptocurrency(cryptocurrencyId: String): Result<List<CryptocurrencyResponseItem>> {
        return getResponse(request = { api.getCryptocurrency(cryptocurrencyId) } )
    }
}