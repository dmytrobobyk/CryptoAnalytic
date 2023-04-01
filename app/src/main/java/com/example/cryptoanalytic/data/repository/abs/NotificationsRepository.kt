package com.example.cryptoanalytic.data.repository.abs

import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    suspend fun getNotifications(): Flow<Result<List<DbNotification>>>
    suspend fun getNotification(notificationId: Long): Flow<Result<DbNotification>>
    suspend fun deleteNotification(notificationId: Long): Flow<Result<Unit>>
    suspend fun saveNotification(notification: DbNotification): Flow<Result<Long>>
    suspend fun updateNotificationActiveState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
    suspend fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
}