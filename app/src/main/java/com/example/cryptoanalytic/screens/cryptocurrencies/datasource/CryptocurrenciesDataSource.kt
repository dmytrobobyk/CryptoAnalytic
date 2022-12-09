package com.example.cryptoanalytic.screens.cryptocurrencies.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.CryptocurrenciesApi
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import javax.inject.Inject

class CryptocurrenciesDataSource @Inject constructor(private val api: CryptocurrenciesApi) : BaseDataSource() {

    suspend fun getLatestCryptocurrencies(): Result<List<CryptocurrencyResponseItem>> {
        return getResponse(request = { api.getLatestCryptocurrencies() })
    }
}