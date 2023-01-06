package com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.database.wrapper.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.CryptocurrencyDetailsApi
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import javax.inject.Inject

class CryptocurrencyDetailsDataSource @Inject constructor(private val api: CryptocurrencyDetailsApi) : BaseDataSource() {

    suspend fun getCryptocurrencyInfo(id: String): Result<CryptocurrencyDetailsResponse> {
        return getResponse(request = { api.getCryptocurrencyInfo(id) })
    }

    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Result<CryptocurrencyHistoryPrices> {
        return getResponse(request = { api.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo) } )
    }
}