package com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.CryptocurrencyDetailsApi
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import javax.inject.Inject

class CryptocurrencyDetailsDataSource @Inject constructor(private val api: CryptocurrencyDetailsApi) : BaseDataSource() {

    suspend fun getCryptocurrencyInfo(id: String): Result<CryptocurrencyDetailsResponse> {
        return getResponse(request = { api.getCryptocurrencyInfo(id) })
    }
}