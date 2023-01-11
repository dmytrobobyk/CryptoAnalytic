package com.example.cryptoanalytic.screens.cryptocurrencyDetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.database.wrapper.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyHistoryPrices
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository.CryptocurrencyDetailsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CryptocurrencyDetailsViewModel @AssistedInject constructor(
    private val repository: CryptocurrencyDetailsRepository,
    @Assisted private val cryptocurrencyId: String,
) : BaseViewModel() {
    private val _cryptocurrencyDetailsInfo = MutableStateFlow<CryptocurrencyDetailsResponse?>(null)
    val cryptocurrencyDetailsInfo: StateFlow<CryptocurrencyDetailsResponse?> = _cryptocurrencyDetailsInfo.asStateFlow()

    private val _cryptocurrencyHistoryPrices = MutableStateFlow<CryptocurrencyHistoryPrices?>(null)
    val cryptocurrencyHistoryPrices: StateFlow<CryptocurrencyHistoryPrices?> = _cryptocurrencyHistoryPrices.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getCryptocurrencyInfo(cryptocurrencyId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            _cryptocurrencyDetailsInfo.value = it

                        }

                    }
                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                }
            }
        }
    }

    fun getCryptocurrencyHistoryPrices(currencySymbol: String, unixTimeFrom: Long, unixTimeTo: Long) {
        viewModelScope.launch {
            repository.getHistoryOfPriceForDateRange(currencySymbol, unixTimeFrom, unixTimeTo).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            _cryptocurrencyHistoryPrices.value = it
                            val ss = ""

                        }

                    }
                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface CryptocurrencyDetailsViewModelFactory {
        fun create(cryptocurrencyId: String): CryptocurrencyDetailsViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: CryptocurrencyDetailsViewModelFactory,
            cryptocurrencyId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                // using our ViewModelFactory's create function
                // to actually create our viewmodel instance
                return assistedFactory.create(cryptocurrencyId) as T
            }
        }
    }
}