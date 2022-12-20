package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.entity.DbCryptocurrency

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * from DB_CRYPTOCURRENCY_DETAILS")
    fun getAll(): List<DbCryptocurrency>?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptocurrencyItemList: List<DbCryptocurrency>)

    @Transaction
    @Delete
    fun deleteAll(cryptocurrencyList: List<DbCryptocurrency>)
}