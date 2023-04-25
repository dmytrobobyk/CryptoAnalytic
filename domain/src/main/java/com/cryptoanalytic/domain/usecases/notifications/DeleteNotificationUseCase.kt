package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long) =
        flow {
            repository.deleteNotification(notificationId).collect { emit(it) }
        }
}