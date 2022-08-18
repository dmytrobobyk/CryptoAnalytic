package com.example.cryptoanalytic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val _openMainScreen: MutableStateFlow<Result<Boolean>> = MutableStateFlow(Result.success(true))
    val openMainScreen: StateFlow<Result<Boolean>> = _openMainScreen.asStateFlow()

}