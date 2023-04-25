package com.cryptoanalytic.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    fun formatPublicationDate(pubDate: String?): String {
        var publicationDate = ""
        try {
            val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val date = pubDate?.let { sourceSdf.parse(it) }
            date?.let {
                val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
                publicationDate = sdf.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return publicationDate
    }
}