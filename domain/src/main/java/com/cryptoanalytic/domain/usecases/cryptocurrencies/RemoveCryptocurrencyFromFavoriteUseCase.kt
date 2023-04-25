package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveCryptocurrencyFromFavoriteUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencyId: String, state: Boolean) =
        flow {
            repository.removeCryptocurrencyFromFavorite(cryptocurrencyId, state).collect { emit(it) }
        }
}