package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converters.CollectionsConverter
import com.example.database.converters.DateConverter
import com.example.database.dao.*
import com.example.database.entity.*

@Database(entities = [DbCryptocurrencyDetails::class, DbCryptocurrency::class, DbLinks::class, DbMarketData::class, DbRoi::class, DbNotification::class, DbNews::class], version = APP_DATABASE_VERSION)
@TypeConverters(CollectionsConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptocurrencyDetailsDao() : CryptocurrencyDetailsDao
    abstract fun cryptocurrencyDao() : CryptocurrencyDao
    abstract fun roiDao() : RoiDao
    abstract fun notificationsDao(): NotificationsDao
    abstract fun newsDao(): NewsDao
}