package com.example.cryptoanalytic.screens.cryptocurrencies.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.database.wrapper.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.repository.CryptocurrenciesRepository
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptocurrenciesViewModel @Inject constructor(private val repository: CryptocurrenciesRepository) : BaseViewModel() {

    private val _cryptocurrenciesList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val cryptocurrenciesList: StateFlow<List<Cryptocurrency>> = _cryptocurrenciesList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getLatestCryptocurrencies().collect { result ->
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
        viewModelScope.launch {
            repository.saveFavoriteCryptocurrencyState(cryptocurrencyId, state).collect { result ->
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