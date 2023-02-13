package com.example.cryptoanalytic.screens.notificationDetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.screens.cryptocurrencies.repository.CryptocurrenciesRepository
import com.example.cryptoanalytic.screens.notificationDetails.repository.NotificationDetailsRepository
import com.example.cryptoanalytic.utils.formatters.mapToDbDataClass
import com.example.cryptoanalytic.utils.formatters.mapToIntermediateDataClass
import com.example.database.embeeded.Cryptocurrency
import com.example.database.intermediate.Notification
import com.example.database.wrapper.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotificationDetailsViewModel @AssistedInject constructor(
    private val repository: NotificationDetailsRepository,
    private val cryptocurrenciesRepository: CryptocurrenciesRepository,
    @Assisted private val notificationId: Long
    ) : BaseViewModel() {

    private val _notification = MutableStateFlow<Notification?>(null)
    val notification: StateFlow<Notification?> = _notification.asStateFlow()

    private val _cryptocurrencies = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val cryptocurrencies: StateFlow<List<Cryptocurrency>> = _cryptocurrencies.asStateFlow()
    val cryptocurrencySpinnerEntries: StateFlow<List<String>> = MutableStateFlow(cryptocurrencies.value.map { it.dbCryptocurrency.symbol }).asStateFlow()

    init {
        viewModelScope.launch {
            repository.getNotification(notificationId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            _notification.value = it.mapToIntermediateDataClass()
                        }
                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                }
            }

            cryptocurrenciesRepository.getCryptocurrencies().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            _cryptocurrencies.value = it
                        }
                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                }
            }
        }
    }

    fun deleteNotification() {
        viewModelScope.launch {
            repository.deleteNotification(notificationId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                        }

                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }

                }
            }
        }
    }

    fun saveNotification() {
        viewModelScope.launch {
            notification.value?.let {
                repository.saveNotification(it.mapToDbDataClass()).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d(TAG, "LOADING")

                        }
                        is Result.Success -> {
                            Log.d(TAG, "SUCCESS")
                            result.data?.let {
                            }

                        }
                        is Result.Finish -> {
                            Log.d(TAG, "FINISH")

                        }
                    }
                }
            }
        }
    }

    fun spinnerItemSelected(item: String) {
        viewModelScope.launch {
            _notification.value?.let {
                it.cryptocurrencyShortName = item
            }
        }
    }

    @AssistedFactory
    interface NotificationDetailsViewModelFactory {
        fun create(notificationId: Long): NotificationDetailsViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: NotificationDetailsViewModelFactory,
            notificationId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // using our ViewModelFactory's create function
                // to actually create our viewmodel instance
                return assistedFactory.create(notificationId) as T
            }
        }
    }
}