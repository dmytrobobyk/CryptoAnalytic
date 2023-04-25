package com.cryptoanalytic.data.datasource.impl

import com.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.cryptoanalytic.data.remote.api.CryptocurrencyService
import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPricesResponse
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyDetailsResponse
import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyResponseItem
import com.cryptoanalytic.utils.getResponse
import com.cryptoanalytic.domain.wrapper.Result
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
    ): Result<CryptocurrencyHistoryPricesResponse> {
        return getResponse(request = { api.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo) } )
    }

    override suspend fun getCryptocurrency(cryptocurrencyId: String): Result<List<CryptocurrencyResponseItem>> {
        return getResponse(request = { api.getCryptocurrency(cryptocurrencyId) } )
    }
}