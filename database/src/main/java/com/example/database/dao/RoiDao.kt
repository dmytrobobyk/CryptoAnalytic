package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.example.database.entity.DbRoi

@Dao
interface RoiDao {

    @Transaction
    @Insert
    fun insert(dbRoi: DbRoi)

}