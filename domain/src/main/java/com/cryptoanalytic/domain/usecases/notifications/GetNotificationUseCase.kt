package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long) =
        flow {
            repository.getNotification(notificationId).collect { emit(it) }
        }
}