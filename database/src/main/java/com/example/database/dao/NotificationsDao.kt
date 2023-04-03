package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.entity.DbNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationsDao {

    @Query("SELECT * FROM DB_NOTIFICATION")
    fun getAll(): Flow<List<DbNotification>>

    @Query("SELECT * FROM DB_NOTIFICATION WHERE DB_NOTIFICATION.notificationId = :notificationId")
    fun getNotificationById(notificationId: Long): Flow<DbNotification>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notification: DbNotification): Long

    @Transaction
    @Insert
    fun update(notification: DbNotification)

    @Transaction
    @Query("UPDATE DB_NOTIFICATION SET isPersistent = :state WHERE notificationId = :notificationId")
    fun updatePersistentState(notificationId: Long, state: Int)

    @Transaction
    @Query("UPDATE DB_NOTIFICATION SET isActive = :state WHERE notificationId = :notificationId")
    fun updateActiveState(notificationId: Long, state: Int)

    @Transaction
    @Query("DELETE FROM DB_NOTIFICATION WHERE notificationId = :notificationId")
    fun delete(notificationId: Long)

    @Transaction
    @Query("DELETE FROM DB_NOTIFICATION")
    fun deleteAll()
}