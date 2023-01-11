package com.example.cryptoanalytic.screens.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.screens.favorites.repository.FavoritesRepository
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository): BaseViewModel() {

    private val _favoritesList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val favoritesList: StateFlow<List<Cryptocurrency>> = _favoritesList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoritesCryptocurrencies().collect { result ->
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
        viewModelScope.launch {
            repository.removeCryptocurrencyFromFavorite(cryptocurrencyId, state).collect { result ->
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