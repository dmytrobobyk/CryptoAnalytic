package com.example.database

import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbNotification
import com.example.database.wrapper.Result
import kotlinx.coroutines.flow.*

class DaoAggregator(private val database: AppDatabase) {

    // Cryptocurrency list
    suspend fun getCryptocurrencyList(): Flow<Result<List<Cryptocurrency>>> {
        return flow {
            database.cryptocurrencyDao().getAll().collect {
                emit(Result.Success(it))
            }
        }
    }

    suspend fun saveCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(cryptocurrencyList.forEach { cryptocurrency ->
                database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency)
                cryptocurrency.dbRoi?.let { it -> database.roiDao().insert(it) }
            }))
        }
    }

    //Cryptocurrency
    suspend fun saveCryptocurrency(cryptocurrency: Cryptocurrency): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(
                database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency).let {
                    cryptocurrency.dbRoi?.let { it -> database.roiDao().insert(it) }
                }
            ))
        }
    }

    suspend fun getCryptocurrency(cryptocurrencyId: String): Flow<Result<Cryptocurrency>> {
        return flow {
            database.cryptocurrencyDao().getCryptocurrencyById(cryptocurrencyId).collect { emit(Result.Success(it)) }
        }
    }

    suspend fun deleteCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(cryptocurrencyList.forEach {
                database.cryptocurrencyDao().delete(it.dbCryptocurrency)
                it.dbRoi?.let { it1 -> database.roiDao().delete(it1) }
            }))
        }
    }

    suspend fun getFavoriteCryptocurrencies(): Flow<Result<List<Cryptocurrency>>> {
        return flow{
            database.cryptocurrencyDao().getFavorites().collect { emit(Result.Success(it)) }
        }
    }

    suspend fun saveCryptocurrencyFavoriteState(cryptocurrencyId: String, state: Boolean): Flow<Result<Unit>> {
        return flow {
            val sqlState = if (state) 1 else 0
            emit(Result.Success(database.cryptocurrencyDao().updateFavoriteState(cryptocurrencyId, sqlState)))
        }
    }

    // Notifications

    suspend fun saveNotification(notification: DbNotification): Flow<Result<Long>> {
        return flow {
            emit(Result.Success(database.notificationsDao().insert(notification)))
        }
    }

    suspend fun deleteNotification(notificationId: Long): Flow<Result<Unit>> {
        return flow {
            emit(Result.Success(database.notificationsDao().delete(notificationId)))
        }
    }

    suspend fun getNotifications(): Flow<Result<List<DbNotification>>> {
        return flow {
            database.notificationsDao().getAll().collect { emit(Result.Success(it)) }
        }
    }

    suspend fun getNotification(notificationId: Long): Flow<Result<DbNotification>> {
        return flow {
            database.notificationsDao().getNotificationById(notificationId).collect{ emit(Result.Success(it)) }
        }
    }

    suspend fun updateNotificationPersistentState(notificationId: Long, state: Boolean): Flow<Result<Unit>> {
        return flow {
            val sqlState = if(state) 1 else 0
            emit(Result.Success(database.notificationsDao().updatePersistentState(notificationId, sqlState)))
        }
    }

    suspend fun updateNotificationActiveState(notificationId: Long, state: Boolean): Flow<Result<Unit>> {
        return flow {
            val sqlState = if(state) 1 else 0
            emit(Result.Success(database.notificationsDao().updateActiveState(notificationId, sqlState)))
        }
    }


}