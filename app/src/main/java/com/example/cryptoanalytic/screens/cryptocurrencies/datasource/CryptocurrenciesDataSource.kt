package com.example.cryptoanalytic.screens.cryptocurrencies.datasource

import com.example.cryptoanalytic.common.BaseDataSource
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.CryptocurrenciesApi
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem
import javax.inject.Inject

class CryptocurrenciesDataSource @Inject constructor(private val api: CryptocurrenciesApi) : BaseDataSource() {

    //CoinMarketCap API
//    suspend fun getLatestCryptocurrencies(): Result<List<LatestResponse>> {
//        return getResponse(request = { api.getLatestCryptocurrencies() })
//    }

    suspend fun getLatestCryptocurrencies(): Result<List<CoinMarketResponseItem>> {
        return getResponse(request = { api.getLatestCryptocurrencies() })
    }
}