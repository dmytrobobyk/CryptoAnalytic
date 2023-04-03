package com.example.cryptoanalytic.presentation.favorites

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.domain.cryptocurrencies.GetFavoritesCryptocurrenciesUseCase
import com.example.cryptoanalytic.domain.cryptocurrencies.RemoveCryptocurrencyFromFavoriteUseCase
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