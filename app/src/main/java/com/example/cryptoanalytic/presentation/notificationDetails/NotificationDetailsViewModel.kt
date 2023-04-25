package com.example.cryptoanalytic.presentation.notificationDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.entity.Cryptocurrency
import com.cryptoanalytic.domain.usecases.cryptocurrencies.GetCryptocurrenciesUseCase
import com.cryptoanalytic.domain.usecases.notifications.GetNotificationUseCase
import com.cryptoanalytic.domain.usecases.notifications.SaveNotificationUseCase
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.di.DispatcherIOScope
import com.cryptoanalytic.domain.wrapper.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _cryptocurrencyAndRoiList = MutableStateFlow<List<Cryptocurrency>>(emptyList())
    val cryptocurrencyAndRoiList: StateFlow<List<Cryptocurrency>> = _cryptocurrencyAndRoiList.asStateFlow()

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
                            _cryptocurrencyAndRoiList.value = it
                            val list = it.map { it.symbol.uppercase() }
                            _cryptocurrencySpinnerEntries.value = list

                            selectedSpinnerItem.value = list.first()
                        }
                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                    else -> {}
                }
            }
        }

        // updating header according to selected item from spinner
        viewModelScope.launch(ioDispatcher) {
            selectedSpinnerItem.collect { symbol ->
                if (symbol.isNotEmpty()) {
                    _cryptoNotification.value = CryptoNotification().apply {
                        _cryptocurrencyAndRoiList.value.firstOrNull { it.symbol == symbol.lowercase() }?.let {
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
                            _cryptoNotification.value = it.mapToLiveModel()
                        }
                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

                    }
                    else -> {}
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

                    else -> {}
                }
            }
        }
    }

    fun saveNotification() {
        viewModelScope.launch(ioDispatcher) {
            cryptoNotification.value.let {
                Log.d(TAG, "Saving notification with ID ${it.notificationId}")
                saveNotificationUseCase(it).collect { result ->
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
                        else -> {}
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