package com.example.cryptoanalytic.domain.cryptocurrencies

import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveFavoriteCryptocurrencyStateUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencyId: String, state: Boolean) =
        flow {
            repository.saveFavoriteCryptocurrencyState(cryptocurrencyId, state).collect { emit(it) }
        }
}