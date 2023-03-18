package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.DB_NEWS

@Entity(tableName = DB_NEWS)
data class DbNews(
    @PrimaryKey(autoGenerate = true)
    val newsId: Long = 0,
    val title: String? = "",
    val link: String? = "",
    val imageUrl: String? = "",
    val publicationDate: String? = "",
    val description: String? = ""
)