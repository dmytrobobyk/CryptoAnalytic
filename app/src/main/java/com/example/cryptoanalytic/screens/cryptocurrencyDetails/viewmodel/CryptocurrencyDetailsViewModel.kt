package com.example.cryptoanalytic.screens.cryptocurrencyDetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.response.CryptocurrencyDetailsResponse
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
    @Assisted("cryptocurrencyId") private val cryptocurrencyId: String,
) : BaseViewModel() {
    private val _cryptocurrencyDetailsInfo = MutableStateFlow<CryptocurrencyDetailsResponse?>(null)
    val cryptocurrencyDetailsInfo: StateFlow<CryptocurrencyDetailsResponse?> = _cryptocurrencyDetailsInfo.asStateFlow()


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

    @AssistedFactory
    interface CryptocurrencyDetailsViewModelFactory {
        fun create(userId: String): CryptocurrencyDetailsViewModel
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