package com.cryptoanalytic.data.repository

import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.entity.Notification
import com.cryptoanalytic.domain.repository.NotificationsRepository
import com.cryptoanalytic.domain.wrapper.Result
import com.cryptoanalytic.utils.mapToDbData
import com.cryptoanalytic.utils.mapToDomain
import com.example.database.DaoAggregator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(private val daoAggregator: DaoAggregator) : NotificationsRepository {

    override suspend fun getNotifications(): Flow<Result<List<Notification>>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotifications().collect {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.map { it.mapToDomain() }))
                }
                emit(Result.Finish)
            }
        }
    }

    override suspend fun getNotification(notificationId: Long): Flow<Result<Notification>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotification(notificationId).collect {
                if (it is Result.Success) {
                    emit(Result.Success(it.data?.mapToDomain()))
                }
            }
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

    override suspend fun saveNotification(notification: CryptoNotification): Flow<Result<Long>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.saveNotification(notification.mapToDbData()).collect { emit(it) }
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