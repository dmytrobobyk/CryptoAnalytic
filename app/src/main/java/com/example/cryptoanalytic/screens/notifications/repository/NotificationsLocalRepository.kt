package com.example.cryptoanalytic.screens.notifications.repository

import com.example.database.DaoAggregator
import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.collect

class NotificationsLocalRepository(private val daoAggregator: DaoAggregator) : NotificationsRepository {

    override fun getNotifications(): Flow<Result<List<DbNotification>>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotifications().collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override fun deleteNotification(notificationId: Long): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.deleteNotification(notificationId).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override fun saveNotification(notification: DbNotification): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.saveNotification(notification).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.updateNotificationPersistentState(notificationId, state).collect { emit(it) }
            emit(Result.Finish)
        }
    }
}