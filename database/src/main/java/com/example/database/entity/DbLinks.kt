package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Relation

@Entity
data class DbLinks(
    @Relation(parentColumn = "id", entityColumn = "cryptocurrencyId")
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