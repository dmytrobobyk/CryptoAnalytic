package com.cryptoanalytic.utils

import android.text.Html
import com.cryptoanalytic.data.remote.api.response.news.RssItem
import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.entity.Cryptocurrency
import com.cryptoanalytic.domain.entity.Notification
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyHistoryPrices
import com.example.database.embeeded.CryptocurrencyAndRoi
import com.example.database.entity.DbCryptocurrency
import com.example.database.entity.DbNews
import com.example.database.entity.DbNotification
import com.example.database.entity.DbRoi

fun CryptoNotification.mapToDbData(): DbNotification {
    with(this) {
        return DbNotification(
            notificationId,
            cryptocurrencyId,
            cryptocurrencyShortName,
            cryptocurrencyName,
            cryptocurrencyPrice,
            cryptocurrencyMarketRank,
            cryptocurrencyImageUrl,
            lessThan,
            moreThan,
            increasedBy,
            decreasedBy,
            changedBy,
            alertFrequency,
            isPersistent,
            isActive,
            notes
        )
    }
}

fun Cryptocurrency.mapToDbData(): CryptocurrencyAndRoi {
    with(this) {
        return CryptocurrencyAndRoi(
            DbCryptocurrency(
                id,
                ath,
                athChangePercentage,
                athDate,
                atl,
                atlChangePercentage,
                atlDate,
                circulatingSupply,
                currentPrice,
                fullyDilutedValuation,
                high24h,
                image,
                lastUpdated,
                low24h,
                marketCap,
                marketCapChange24h,
                marketCapChangePercentage24h,
                marketCapRank,
                maxSupply,
                name,
                priceChange24h,
                priceChangePercentage24h,
                symbol,
                totalSupply,
                totalVolume,
                isFavorite
            ),
            roi?.let { it ->
                DbRoi(
                    it.cryptocurrencyId,
                    it.currency,
                    it.percentage,
                    it.times
                )
            }
        )
    }
}

fun RssItem.mapToDbData(): DbNews {
    with(this) {
        return DbNews(
            title = title,
            link = link,
            imageUrl = imageUrl,
            publicationDate = DateTimeUtil.formatPublicationDate(pubDate),
            description = Html.fromHtml(description).split("\n")[2]
        )
    }
}

fun Notification.mapToDbData(): DbNotification {
    with(this) {
        return DbNotification(
            notificationId,
            cryptocurrencyId,
            cryptocurrencyShortName,
            cryptocurrencyName,
            cryptocurrencyPrice,
            cryptocurrencyMarketRank,
            cryptocurrencyImageUrl,
            lessThan,
            moreThan,
            increasedBy,
            decreasedBy,
            changedBy,
            alertFrequency,
            isPersistent,
            isActive,
            notes
        )
    }
}