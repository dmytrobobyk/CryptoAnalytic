package com.example.database.dao

import androidx.room.*
import com.example.database.entity.DbRoi

@Dao
interface RoiDao {

    @Query("SELECT * FROM DB_ROI WHERE DB_ROI.cryptocurrencyId = :id")
    fun getRoiById(id: String): DbRoi

    @Transaction
    @Insert
    fun insert(dbRoi: DbRoi)

    @Transaction
    @Delete
    fun delete(dbRoi: DbRoi)

}