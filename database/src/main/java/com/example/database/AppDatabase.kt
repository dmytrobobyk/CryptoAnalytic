package com.example.database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converters.CollectionsConverter
import com.example.database.converters.DateConverter

@TypeConverters(CollectionsConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {


}