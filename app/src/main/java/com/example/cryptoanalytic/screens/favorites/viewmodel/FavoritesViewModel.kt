package com.example.cryptoanalytic.screens.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.screens.favorites.repository.FavoritesRepository
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import com.example.cryptoanalytic.common.Result
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

    //TODO: add logic to remove the item from favorites
    fun removeCryptocurrencyFromFavorite() {

    }


}