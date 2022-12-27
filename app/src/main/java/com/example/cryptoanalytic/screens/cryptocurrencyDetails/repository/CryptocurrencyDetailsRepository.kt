package com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyDetailsRepository {
    suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>>
    suspend fun getHistoryOfPriceForDateRange(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long): Flow<Result<CryptocurrencyHistoryPrices>>
}