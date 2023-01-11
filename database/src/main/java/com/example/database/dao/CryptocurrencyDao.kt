package com.example.database.dao

import androidx.room.*
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM DB_CRYPTOCURRENCY")
    fun getAll(): Flow<List<Cryptocurrency>>

    @Query("SELECT * FROM DB_CRYPTOCURRENCY WHERE DB_CRYPTOCURRENCY.isFavorite = 1")
    fun getFavorites(): Flow<List<Cryptocurrency>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptocurrencies: MutableList<DbCryptocurrency>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptocurrency: DbCryptocurrency)

    @Transaction
    @Update
    fun update(cryptocurrency: DbCryptocurrency)

    @Transaction
    @Query("UPDATE DB_CRYPTOCURRENCY SET isFavorite = :state WHERE id = :cryptocurrencyId")
    fun updateFavoriteState(cryptocurrencyId: String, state: Int)

    @Transaction
    @Delete
    fun deleteAll(cryptocurrencies: List<DbCryptocurrency>)

    @Transaction
    @Delete
    fun delete(cryptocurrency: DbCryptocurrency)
}