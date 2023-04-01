package com.example.cryptoanalytic.domain.notifications

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNotificationActiveStateUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long, state: Boolean) =
        flow {
            repository.updateNotificationActiveState(notificationId, state).collect { emit(it) }
        }
}