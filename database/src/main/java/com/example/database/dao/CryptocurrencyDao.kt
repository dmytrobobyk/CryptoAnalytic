package com.example.database.dao

import androidx.room.*
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM DB_CRYPTOCURRENCY")
    fun getAll(): List<Cryptocurrency>

    @Query("SELECT * FROM DB_CRYPTOCURRENCY WHERE DB_CRYPTOCURRENCY.isFavorite = 'true'")
    fun getFavorites(): List<Cryptocurrency>

    @Transaction
    @Insert
    fun insert(cryptocurrencies: MutableList<DbCryptocurrency>)

    @Transaction
    @Insert
    fun insert(cryptocurrency: DbCryptocurrency)

    @Transaction
    @Delete
    fun deleteAll(cryptocurrencies: List<DbCryptocurrency>)

    @Transaction
    @Delete
    fun delete(cryptocurrency: DbCryptocurrency)
}