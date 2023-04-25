package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptocurrenciesUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getCryptocurrencies().collect { emit(it) }
        }
}
