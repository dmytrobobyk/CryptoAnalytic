package com.example.cryptoanalytic.presentation.cryptocurrencies

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.domain.cryptocurrencies.GetCryptocurrenciesUseCase
import com.example.cryptoanalytic.domain.cryptocurrencies.SaveFavoriteCryptocurrencyStateUseCase
import com.example.database.embeeded.Cryptocurrency
import com.example.database.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptocurrenciesViewModel @Inject constructor(
    private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase,
    private val saveFavoriteCryptocurrencyStateUseCase: SaveFavoriteCryptocurrencyStateUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _cryptocurrenciesList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val cryptocurrenciesList: StateFlow<List<Cryptocurrency>> = _cryptocurrenciesList.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            getCryptocurrenciesUseCase().collect { result ->
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
                            _cryptocurrenciesList.value = it
                        }

                    }
                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                }
            }
        }
    }

    fun saveFavoriteCryptocurrencyState(cryptocurrencyId: String, state: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            saveFavoriteCryptocurrencyStateUseCase(cryptocurrencyId, state).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS FAVORITE STATE SAVED")
                        result.data?.let {
                        }

                    }
                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                }
            }
        }
    }


}