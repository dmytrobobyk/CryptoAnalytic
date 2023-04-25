package com.cryptoanalytic.domain.usecases.notifications

import com.cryptoanalytic.domain.repository.NotificationsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getNotifications().collect { emit(it) }
        }
}