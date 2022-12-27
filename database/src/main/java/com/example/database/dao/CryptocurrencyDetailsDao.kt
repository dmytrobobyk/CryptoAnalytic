package com.example.database.dao

import androidx.room.*
import com.example.database.entity.DbCryptocurrencyDetails

@Dao
interface CryptocurrencyDetailsDao {

    @Query("SELECT * FROM DB_CRYPTOCURRENCY_DETAILS WHERE DB_CRYPTOCURRENCY_DETAILS.cryptocurrencyDetailsId = :id")
    fun getCryptocurrencyById(id: String): DbCryptocurrencyDetails?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptocurrency: DbCryptocurrencyDetails)

    @Transaction
    @Update
    fun update(cryptocurrency: DbCryptocurrencyDetails)

    @Transaction
    @Delete
    fun delete(cryptocurrency: DbCryptocurrencyDetails)
}