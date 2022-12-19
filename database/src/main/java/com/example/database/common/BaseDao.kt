package com.example.database.common

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface BaseDao<T> {

    @Transaction
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: T): Long

    @Transaction
    @Insert(onConflict = REPLACE)
    suspend fun insert(items: List<T>): List<Long>

    @Transaction
    @Update(onConflict = REPLACE)
    suspend fun update(item: T): Int

    @Transaction
    @Update(onConflict = REPLACE)
    suspend fun update(items: List<T>): Int

    @Transaction
    @Delete
    suspend fun delete(item: T): Int

    @Transaction
    @Delete
    suspend fun delete(items: List<T>): Int
}