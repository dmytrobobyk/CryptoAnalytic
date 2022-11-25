package com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import kotlinx.coroutines.flow.Flow

interface CryptocurrencyDetailsRepository {
    suspend fun getCryptocurrencyInfo(id: String): Flow<Result<CryptocurrencyDetailsResponse>>
}