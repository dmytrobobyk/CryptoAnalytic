package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveFavoriteCryptocurrencyStateUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencyId: String, state: Boolean) =
        flow {
            repository.saveFavoriteCryptocurrencyState(cryptocurrencyId, state).collect { emit(it) }
        }
}