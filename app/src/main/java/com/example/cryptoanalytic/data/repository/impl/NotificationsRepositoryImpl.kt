package com.example.cryptoanalytic.data.repository.impl

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import com.example.database.DaoAggregator
import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(private val daoAggregator: DaoAggregator) : NotificationsRepository {

    override suspend fun getNotifications(): Flow<Result<List<DbNotification>>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotifications().collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun getNotification(notificationId: Long): Flow<Result<DbNotification>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotification(notificationId).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun deleteNotification(notificationId: Long): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.deleteNotification(notificationId).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun saveNotification(notification: DbNotification): Flow<Result<Long>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.saveNotification(notification).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun updateNotificationActiveState(notificationId: Long, state: Boolean): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.updateNotificationActiveState(notificationId, state).collect { emit(it) }
            emit(Result.Finish)
        }
    }

    override suspend fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.updateNotificationPersistentState(notificationId, state).collect { emit(it) }
            emit(Result.Finish)
        }
    }
}