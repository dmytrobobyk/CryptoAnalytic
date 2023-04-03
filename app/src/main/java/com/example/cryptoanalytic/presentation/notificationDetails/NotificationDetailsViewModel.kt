package com.example.cryptoanalytic.presentation.notificationDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.domain.cryptocurrencies.GetCryptocurrenciesUseCase
import com.example.cryptoanalytic.domain.notifications.GetNotificationUseCase
import com.example.cryptoanalytic.domain.notifications.SaveNotificationUseCase
import com.example.cryptoanalytic.utils.formatters.mapToDbDataClass
import com.example.cryptoanalytic.utils.formatters.mapToIntermediateDataClass
import com.example.database.embeeded.Cryptocurrency
import com.example.database.intermediate.CryptoNotification
import com.example.database.wrapper.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationDetailsViewModel @AssistedInject constructor(
    private val getCryptocurrenciesUseCase: GetCryptocurrenciesUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val removeNotificationUseCase: GetNotificationUseCase,
    private val saveNotificationUseCase: SaveNotificationUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher,
    @Assisted private val notificationId: Long
) : BaseViewModel() {

    private val _cryptoNotification = MutableStateFlow(CryptoNotification())
    val cryptoNotification: StateFlow<CryptoNotification> = _cryptoNotification.asStateFlow()

    private val _cryptocurrencyList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val cryptocurrencyList: StateFlow<List<Cryptocurrency>> = _cryptocurrencyList.asStateFlow()

    private val _cryptocurrencySpinnerEntries = MutableStateFlow<List<String>>(emptyList())
    val cryptocurrencySpinnerEntries: StateFlow<List<String>> = _cryptocurrencySpinnerEntries.asStateFlow()

    val selectedSpinnerItem = MutableStateFlow("")

    init {
        viewModelScope.launch(ioDispatcher) {
            getCryptocurrenciesUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            _cryptocurrencyList.value = it
                            val list = it.map { it.dbCryptocurrency.symbol.uppercase() }
                            _cryptocurrencySpinnerEntries.value = list

                            selectedSpinnerItem.value = list.first()
                        }
                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                }
            }
        }

        // updating header according to selected item from spinner
        viewModelScope.launch(ioDispatcher) {
            selectedSpinnerItem.collect { symbol ->
                if (symbol.isNotEmpty()) {
                    _cryptoNotification.value = CryptoNotification().apply {
                        _cryptocurrencyList.value.firstOrNull { it.dbCryptocurrency.symbol == symbol.lowercase() }?.dbCryptocurrency?.let {
                            cryptocurrencyName = it.name
                            cryptocurrencyShortName = it.symbol
                            cryptocurrencyImageUrl = it.image
                            cryptocurrencyId = it.id
                            cryptocurrencyPrice = it.currentPrice
                        }
                    }
                }
            }
        }

        viewModelScope.launch(ioDispatcher) {
            getNotificationUseCase(notificationId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS")
                        result.data?.let {
                            selectedSpinnerItem.value = it.cryptocurrencyShortName
                            _cryptoNotification.value = it.mapToIntermediateDataClass()
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
        viewModelScope.launch(ioDispatcher) {
            removeNotificationUseCase(notificationId).collect { result ->
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
        viewModelScope.launch(ioDispatcher) {
            cryptoNotification.value.let {
                Log.d(TAG, "Saving notification with ID ${it.notificationId}")
                saveNotificationUseCase(it.mapToDbDataClass()).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d(TAG, "LOADING")

                        }
                        is Result.Success -> {
                            result.data?.let {
                                _cryptoNotification.value.notificationId = it
                                Log.d(TAG, "Saved notification: notificationId = $it")
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