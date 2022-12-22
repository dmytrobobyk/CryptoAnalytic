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

//    @Query("SELECT * from DB_CRYPTOCURRENCY_DETAILS")
//    fun getAll(): List<DbCryptocurrency?>

    @Query("SELECT * from DB_CRYPTOCURRENCY LEFT JOIN DB_ROI ON DB_ROI.cryptocurrencyId = DB_CRYPTOCURRENCY.id")
    fun getAll(): List<DbCryptocurrency?>

    @Transaction
    @Query("INSERT INTO DB_CRYPTOCURRENCY VALUES(1, 1.0, 1.0, \"date\", 1.0, 1.0) INSERT INTO DB_ROI VALUES()")
    fun insert(cryptocurrencies: List<DbCryptocurrency>)

//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(cryptocurrencyItemList: List<DbCryptocurrency>)

    @Transaction
    @Delete
    fun deleteAll(cryptocurrencies: List<DbCryptocurrency>)
}