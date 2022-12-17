package com.example.database.common

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
abstract class BaseDao<T>(val database: RoomDatabase) {

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(item: T): Long

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(items: List<T>): List<Long>

    @Update(onConflict = REPLACE)
    abstract suspend fun update(item: T): Int

    @Update(onConflict = REPLACE)
    abstract suspend fun update(items: List<T>): Int

    @Delete
    abstract suspend fun delete(item: T): Int

    @Delete
    abstract suspend fun delete(items: List<T>): Int
}