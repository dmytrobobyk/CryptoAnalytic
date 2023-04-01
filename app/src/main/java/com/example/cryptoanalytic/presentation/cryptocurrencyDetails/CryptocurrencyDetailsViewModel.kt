package com.example.cryptoanalytic.presentation.cryptocurrencyDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyDetailsResponse
import com.example.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPrices
import com.example.cryptoanalytic.domain.cryptocurrencies.GetCryptocurrencyInfoUseCase
import com.example.cryptoanalytic.domain.cryptocurrencies.GetHistoryOfPriceForDateRangeUseCase
import com.example.database.wrapper.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CryptocurrencyDetailsViewModel @AssistedInject constructor(
    private val getCryptocurrencyInfoUseCase: GetCryptocurrencyInfoUseCase,
    private val getHistoryOfPriceForDateRangeUseCase: GetHistoryOfPriceForDateRangeUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val cryptocurrencyId: String,
) : BaseViewModel() {
    private val _cryptocurrencyDetailsInfo = MutableStateFlow<CryptocurrencyDetailsResponse?>(null)
    val cryptocurrencyDetailsInfo: StateFlow<CryptocurrencyDetailsResponse?> = _cryptocurrencyDetailsInfo.asStateFlow()

    private val _cryptocurrencyHistoryPrices = MutableStateFlow<CryptocurrencyHistoryPrices?>(null)
    val cryptocurrencyHistoryPrices: StateFlow<CryptocurrencyHistoryPrices?> = _cryptocurrencyHistoryPrices.asStateFlow()


    init {
        viewModelScope.launch(ioDispatcher) {
            getCryptocurrencyInfoUseCase(cryptocurrencyId).collect { result ->
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
        viewModelScope.launch(ioDispatcher) {
            getHistoryOfPriceForDateRangeUseCase(currencySymbol, unixTimeFrom, unixTimeTo).collect { result ->
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