package com.example.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.database.DB_LINKS

@Entity(tableName = DB_LINKS)
data class DbLinks(
    @PrimaryKey(autoGenerate = true)
    val linksId: Int,
//    @Relation(parentColumn = "cryptocurrencyDetailsId", entityColumn = "cryptocurrencyId", entity = DbCryptocurrencyDetails::class)
    val cryptocurrencyId: String,
    val announcementUrl: List<String>,
    val bitcoinTalkThreadIdentifier: String,
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