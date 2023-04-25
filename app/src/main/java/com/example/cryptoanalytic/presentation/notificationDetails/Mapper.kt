package com.example.cryptoanalytic.presentation.notificationDetails

import com.cryptoanalytic.domain.entity.CryptoNotification
import com.cryptoanalytic.domain.entity.Notification

fun Notification.mapToLiveModel(): CryptoNotification {
    with(this) {
        return CryptoNotification(
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