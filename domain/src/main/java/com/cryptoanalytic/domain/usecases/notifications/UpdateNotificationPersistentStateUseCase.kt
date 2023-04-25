package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNotificationPersistentStateUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long, state: Boolean) =
        flow {
            repository.updateNotificationPersistentState(notificationId, state).collect { emit(it) }
        }
}