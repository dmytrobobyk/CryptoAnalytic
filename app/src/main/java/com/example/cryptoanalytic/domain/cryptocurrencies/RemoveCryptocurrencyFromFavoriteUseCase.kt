package com.example.cryptoanalytic.domain.cryptocurrencies

import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveCryptocurrencyFromFavoriteUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencyId: String, state: Boolean) =
        flow {
            repository.removeCryptocurrencyFromFavorite(cryptocurrencyId, state).collect { emit(it) }
        }
}