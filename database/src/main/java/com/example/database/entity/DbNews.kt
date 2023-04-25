package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DB_NEWS

@Entity(tableName = DB_NEWS)
data class DbNews(
    @PrimaryKey(autoGenerate = true)
    var newsId: Long = 0,
    var title: String? = "",
    var link: String? = "",
    var imageUrl: String? = "",
    var publicationDate: String? = "",
    var description: String? = ""
)