package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveNotificationUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(notification: CryptoNotification) =
        flow {
            repository.saveNotification(notification).collect { emit(it) }
        }
}