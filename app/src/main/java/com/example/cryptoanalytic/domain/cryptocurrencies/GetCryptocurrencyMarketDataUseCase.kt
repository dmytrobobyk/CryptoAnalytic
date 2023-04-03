package com.example.cryptoanalytic.domain.cryptocurrencies

import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptocurrencyMarketDataUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencySymbol: String) =
        flow {
            repository.getCryptocurrencyMarketData(cryptocurrencySymbol).collect { emit(it) }
        }
}