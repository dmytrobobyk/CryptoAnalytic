package com.example.cryptoanalytic.screens.notificationDetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.screens.notificationDetails.repository.NotificationDetailsRepository
import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationDetailsViewModel @AssistedInject constructor(
    private val repository: NotificationDetailsRepository,
    @Assisted private val notificationId: Long
    ) : BaseViewModel() {

    private val _notification = MutableStateFlow<DbNotification?>(null)
    val notification: StateFlow<DbNotification?> = _notification.asStateFlow()

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
                            _notification.value = it
                        }

                    }
                    is Result.Finish -> {
                        Log.d(TAG, "FINISH")

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