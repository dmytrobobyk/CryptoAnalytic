package com.example.cryptoanalytic.presentation.favorites

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.cryptoanalytic.domain.entity.Cryptocurrency
import com.cryptoanalytic.domain.usecases.cryptocurrencies.GetFavoritesCryptocurrenciesUseCase
import com.cryptoanalytic.domain.usecases.cryptocurrencies.RemoveCryptocurrencyFromFavoriteUseCase
import com.cryptoanalytic.domain.wrapper.Result
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.di.DispatcherIOScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesCryptocurrenciesUseCase: GetFavoritesCryptocurrenciesUseCase,
    private val removeCryptocurrencyFromFavoriteUseCase: RemoveCryptocurrencyFromFavoriteUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher
    ): BaseViewModel() {

    private val _favoritesList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val favoritesList: StateFlow<List<Cryptocurrency>> = _favoritesList.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            getFavoritesCryptocurrenciesUseCase().collect { result ->
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
                            _favoritesList.value = it
                        }

                    }

                    is Result.Error -> {
                        Log.d(TAG, "ERROR")
                    }
                    else -> {}
                }
            }
        }
    }

    fun removeCryptocurrencyFromFavorite(cryptocurrencyId: String, state: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            removeCryptocurrencyFromFavoriteUseCase(cryptocurrencyId, state).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }

                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }

                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS REMOVED FROM FAVORITE")
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