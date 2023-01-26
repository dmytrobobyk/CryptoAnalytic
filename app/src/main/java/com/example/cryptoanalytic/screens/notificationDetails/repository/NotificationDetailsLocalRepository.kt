package com.example.cryptoanalytic.screens.notificationDetails.repository

import com.example.database.DaoAggregator
import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class NotificationDetailsLocalRepository(private val daoAggregator: DaoAggregator) : NotificationDetailsRepository {
    override fun getNotification(notificationId: Long): Flow<Result<DbNotification>> {
        return flow {
            emit(Result.Loading)
            daoAggregator.getNotification(notificationId).collect { emit(it) }
            emit(Result.Finish)
        }
    }
}
