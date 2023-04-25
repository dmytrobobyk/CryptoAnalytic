package com.cryptoanalytic.domain.usecases.cryptocurrencies

import com.cryptoanalytic.domain.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHistoryOfPriceForDateRangeUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long) =
        flow {
            repository.getHistoryOfPriceForDateRange(cryptocurrencySymbol, unixTimeFrom, unixTimeTo).collect { emit(it) }
        }
}