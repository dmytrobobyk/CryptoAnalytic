package com.example.cryptoanalytic.utils.formatters

import com.example.database.entity.DbNotification
import com.example.database.intermediate.Notification

fun DbNotification.mapToIntermediateDataClass(): Notification {
    with(this) {
        return Notification(
            notificationId,
            cryptocurrencyId,
            cryptocurrencyShortName,
            cryptocurrencyName,
            cryptocurrencyMarketRank,
            cryptocurrencyImageUrl,
            lessThan,
            moreThan,
            increasedBy,
            decreasedBy,
            changedBy,
            alertFrequency,
            isPersistent,
            notes
        )
    }
}

fun Notification.mapToDbDataClass(): DbNotification {
    with(this) {
        return DbNotification(
            notificationId,
            cryptocurrencyId,
            cryptocurrencyShortName,
            cryptocurrencyName,
            cryptocurrencyMarketRank,
            cryptocurrencyImageUrl,
            lessThan,
            moreThan,
            increasedBy,
            decreasedBy,
            changedBy,
            alertFrequency,
            isPersistent,
            notes
        )
    }
}
