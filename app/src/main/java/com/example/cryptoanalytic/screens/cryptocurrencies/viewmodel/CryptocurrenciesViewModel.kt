package com.example.cryptoanalytic.screens.cryptocurrencies.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.screens.cryptocurrencies.repository.CryptocurrenciesRepository
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptocurrenciesViewModel @Inject constructor(private val repository: CryptocurrenciesRepository) : BaseViewModel() {
//    private val TAG = CryptocurrenciesViewModel::class.java.name

    //CoinMarketCap API
//    private val _cryptocurrenciesList = MutableStateFlow<List<LatestResponse>>(emptyList())
//    val cryptocurrenciesList: StateFlow<List<LatestResponse>> = _cryptocurrenciesList.asStateFlow()

    //CoinGecko API
    private val _cryptocurrenciesList = MutableStateFlow<List<CoinMarketResponseItem>>(emptyList())
    val cryptocurrenciesList: StateFlow<List<CoinMarketResponseItem>> = _cryptocurrenciesList.asStateFlow()

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