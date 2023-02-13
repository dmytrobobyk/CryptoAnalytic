package com.example.cryptoanalytic.screens.notificationDetails.repository

import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.Flow
import com.example.database.wrapper.Result


interface NotificationDetailsRepository {
    fun getNotification(notificationId: Long): Flow<Result<DbNotification>>
    fun deleteNotification(notificationId: Long): Flow<Result<Unit>>
    fun saveNotification(notification: DbNotification): Flow<Result<Unit>>
}