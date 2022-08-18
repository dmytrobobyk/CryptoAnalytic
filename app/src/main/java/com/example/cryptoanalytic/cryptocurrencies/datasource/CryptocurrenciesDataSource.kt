package com.example.cryptoanalytic.cryptocurrencies.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import com.example.cryptoanalytic.cryptocurrencies.api.CryptocurrenciesApi
import javax.inject.Inject

class CryptocurrenciesDataSource @Inject constructor(private val api: CryptocurrenciesApi) : BaseDataSource() {

    suspend fun getLatestCryptocurrencies(): Result<List<LatestResponse>> {
        return getResponse(request = { api.getLatestCryptocurrencies() })
    }
}