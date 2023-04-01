package com.example.cryptoanalytic.domain.notifications

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getNotifications().collect { emit(it) }
        }
}