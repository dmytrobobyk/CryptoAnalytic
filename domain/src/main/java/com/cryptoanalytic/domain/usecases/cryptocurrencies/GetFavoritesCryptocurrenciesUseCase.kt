package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesCryptocurrenciesUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getFavoritesCryptocurrencies().collect { emit(it) }
        }
}