package com.example.cryptoanalytic.domain.notifications

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notificationId: Long) =
        flow {
            repository.deleteNotification(notificationId).collect { emit(it) }
        }
}