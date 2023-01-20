package com.example.cryptoanalytic.screens.notifications.repository

import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.Flow
import com.example.database.wrapper.Result

interface NotificationsRepository {
    fun getNotifications(): Flow<Result<List<DbNotification>>>
    fun deleteNotification(notification: DbNotification): Flow<Result<Unit>>
    fun saveNotification(notification: DbNotification): Flow<Result<Unit>>
    fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>>
}