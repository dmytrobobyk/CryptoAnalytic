package com.example.cryptoanalytic.presentation.notifications

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.cryptoanalytic.domain.entity.Notification
import com.cryptoanalytic.domain.usecases.notifications.GetNotificationsUseCase
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
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher
    ) : BaseViewModel() {

    private val _notificationList = MutableStateFlow<List<Notification>>(emptyList())
    val notificationList: StateFlow<List<Notification>> = _notificationList.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            getNotificationsUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        Log.d(TAG, "LOADING")

                    }
                    is Result.Success -> {
                        Log.d(TAG, "SUCCESS: notification list received")
                        result.data?.let {
                            _notificationList.value = it
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