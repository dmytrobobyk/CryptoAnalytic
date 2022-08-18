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

    //    private val _cryptocurrenciesList = MutableSharedFlow<List<LatestResponse>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
//    val cryptocurrenciesList: SharedFlow<List<LatestResponse>> = _cryptocurrenciesList.asSharedFlow()
    private val _cryptocurrenciesList = MutableStateFlow<List<LatestResponse>>(emptyList())
    val cryptocurrenciesList: StateFlow<List<LatestResponse>> = _cryptocurrenciesList

    init {
        viewModelScope.launch {
            repository.getLatestCryptocurrencies().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
//                    is Result.Finish -> {
//
//                    }
                    is Result.Success -> {
                        result.data?.let {
                            _cryptocurrenciesList.emit(it)
                        }

                    }
                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                }
            }
//            _cryptocurrenciesList.emit(LatestResponse(null, null, null,null, null, null,null, null, null,null, null, null,null, null, null,null))
        }
    }


}