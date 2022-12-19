package com.example.database.dao

import androidx.room.*
import com.example.database.entity.DbCryptocurrency

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM DB_CRYPTOCURRENCY WHERE id = :id")
    fun getCryptocurrencyById(id: String): DbCryptocurrency?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(connection: DbCryptocurrency)

    @Update
    fun update(connection: DbCryptocurrency)

    @Delete
    fun delete(connection: DbCryptocurrency)
}