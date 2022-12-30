package com.example.database.dao

import androidx.room.*
import com.example.database.entity.DbRoi
import kotlinx.coroutines.flow.Flow

@Dao
interface RoiDao {

    @Query("SELECT * FROM DB_ROI WHERE DB_ROI.cryptocurrencyId = :id")
    fun getRoiById(id: String): Flow<DbRoi>

    @Transaction
    @Insert
    fun insert(dbRoi: DbRoi)

    @Transaction
    @Delete
    fun delete(dbRoi: DbRoi)

}