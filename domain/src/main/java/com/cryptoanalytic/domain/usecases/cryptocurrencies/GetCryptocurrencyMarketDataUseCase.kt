package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptocurrencyMarketDataUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencySymbol: String) =
        flow {
            repository.getCryptocurrencyMarketData(cryptocurrencySymbol).collect { emit(it) }
        }
}