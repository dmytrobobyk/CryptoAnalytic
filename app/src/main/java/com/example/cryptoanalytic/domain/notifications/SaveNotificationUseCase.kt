package com.example.cryptoanalytic.domain.notifications

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveNotificationUseCase @Inject constructor(private val repository: NotificationsRepository) {

    suspend operator fun invoke(dbNotification: DbNotification) =
        flow {
            repository.saveNotification(dbNotification).collect { emit(it) }
        }
}