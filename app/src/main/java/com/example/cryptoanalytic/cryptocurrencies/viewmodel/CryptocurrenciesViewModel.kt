package com.example.cryptoanalytic.cryptocurrencies.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import com.example.cryptoanalytic.cryptocurrencies.repository.CryptocurrenciesRepository
import kotlinx.coroutines.channels.BufferOverflow
import com.example.cryptoanalytic.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptocurrenciesViewModel @Inject constructor(private val repository: CryptocurrenciesRepository) : BaseViewModel() {
    private val TAG = CryptocurrenciesViewModel::class.java.name

    private val _cryptocurrenciesList = MutableStateFlow<List<LatestResponse>>(emptyList())
    val cryptocurrenciesList: StateFlow<List<LatestResponse>> = _cryptocurrenciesList.asStateFlow()

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


}