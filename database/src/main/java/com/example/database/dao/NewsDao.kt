package com.example.database.dao

import androidx.room.*
import com.example.database.entity.DbNews
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM DB_NEWS")
    fun getAll(): Flow<List<DbNews>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: List<DbNews>)

    @Transaction
    @Query("DELETE FROM DB_NEWS")
    fun deleteAll()

}



