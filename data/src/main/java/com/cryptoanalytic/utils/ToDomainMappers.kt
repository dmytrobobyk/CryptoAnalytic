package com.cryptoanalytic.utils

import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyHistoryPricesResponse
import com.cryptoanalytic.data.remote.api.response.cryptocurrency.CryptocurrencyResponseItem
import com.cryptoanalytic.domain.entity.*
import com.cryptoanalytic.domain.entity.cryptocurrencyDetails.CryptocurrencyHistoryPrices
import com.example.database.embeeded.CryptocurrencyAndRoi
import com.example.database.entity.DbNews
import com.example.database.entity.DbNotification

fun CryptocurrencyResponseItem.mapToDomain(): Cryptocurrency {
    with(this) {
        return Cryptocurrency(
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
            isFavorite = false,
            roiResponse?.let {
                Roi(
                    id,
                    roiResponse.currency,
                    roiResponse.percentage,
                    roiResponse.times
                )
            }
        )
    }
}

fun CryptoNotification.mapToDomain(): Notification {
    with(this) {
        return Notification(
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

fun CryptocurrencyAndRoi.mapToDomain(): Cryptocurrency {
    with(dbCryptocurrency) {
        return Cryptocurrency(
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
            isFavorite,
            dbRoi?.let { it ->
                Roi(
                    it.cryptocurrencyId,
                    it.currency,
                    it.percentage,
                    it.times
                )
            }
        )
    }
}

fun DbNews.mapToDomain(): News {
    with(this) {
        return News(
            newsId,
            title,
            link,
            imageUrl,
            publicationDate,
            description
        )
    }
}

fun CryptocurrencyHistoryPricesResponse.mapToDomain(): CryptocurrencyHistoryPrices {
    return with(this) {
        CryptocurrencyHistoryPrices(
            marketCaps,
            prices,
            totalVolumes
        )
    }
}

fun DbNotification.mapToDomain(): Notification {
    with(this) {
        return Notification(
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
