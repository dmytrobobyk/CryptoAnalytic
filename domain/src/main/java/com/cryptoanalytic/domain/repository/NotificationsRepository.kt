package com.cryptoanalytic.domain.repository

import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.entity.Notification
import kotlinx.coroutines.flow.Flow
import com.cryptoanalytic.domain.wrapper.Result

interface NotificationsRepository {
    suspend fun getNotifications(): Flow<Result<List<Notification>>>
    suspend fun getNotification(notificationId: Long): Flow<Result<Notification>>
    suspend fun deleteNotification(notificationId: Long): Flow<Result<Unit>>
    suspend fun saveNotification(notification: CryptoNotification): Flow<Result<Long>>
    suspend fun updateNotificationActiveState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
    suspend fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
}