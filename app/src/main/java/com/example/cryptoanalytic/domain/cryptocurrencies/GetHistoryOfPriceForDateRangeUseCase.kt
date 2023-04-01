package com.example.cryptoanalytic.domain.cryptocurrencies

import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHistoryOfPriceForDateRangeUseCase @Inject constructor(private val repository: CryptocurrencyRepository) {

    suspend operator fun invoke(cryptocurrencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long) =
        flow {
            repository.getHistoryOfPriceForDateRange(cryptocurrencySymbol, unixTimeFrom, unixTimeTo).collect { emit(it) }
        }
}