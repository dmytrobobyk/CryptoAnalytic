package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity
data class DbLinks(
    @ForeignKey(DbCryptocurrency::class.java, "id", "cryptocurrencyId", onDelete = CASCADE, onUpdate = CASCADE)
    val cryptocurrencyId: String,
    val announcementUrl: List<String>,
    val bitcointalkThreadIdentifier: Any,
    val blockchainSite: List<String>,
    val chatUrl: List<String>,
    val facebookUsername: String,
    val homepage: List<String>,
    val officialForumUrl: List<String>,
    val bitbucket: List<String>,
    val github: List<String>,
    val subredditUrl: String,
    val telegramChannelIdentifier: String,
    val twitterScreenName: String
)