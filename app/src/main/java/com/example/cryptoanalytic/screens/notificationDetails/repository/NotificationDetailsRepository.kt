package com.example.cryptoanalytic.screens.notificationDetails.repository

import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.Flow
import com.example.database.wrapper.Result


interface NotificationDetailsRepository {
    suspend fun getNotifications(): Flow<Result<List<DbNotification>>>
    suspend fun getNotification(notificationId: Long): Flow<Result<DbNotification>>
    suspend fun deleteNotification(notificationId: Long): Flow<Result<Unit>>
    suspend fun saveNotification(notification: DbNotification): Flow<Result<Long>>
    suspend fun updateNotificationActiveState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
}