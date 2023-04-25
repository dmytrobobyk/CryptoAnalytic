package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNotificationActiveStateUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long, state: Boolean) =
        flow {
            repository.updateNotificationActiveState(notificationId, state).collect { emit(it) }
        }
}