package com.cryptoanalytic.domain.entity

class CryptoNotification (
    var notificationId: Long = 0,
    var cryptocurrencyId: String = "",
    var cryptocurrencyShortName: String = "",
    var cryptocurrencyName: String = "",
    var cryptocurrencyPrice: Double = 0.0,
    var cryptocurrencyMarketRank: Int = 0,
    var cryptocurrencyImageUrl: String = "",
    var lessThan: Double = 20000.0,
    var moreThan: Double = 0.0,
    var increasedBy: Double = 0.0,
    var decreasedBy: Double = 0.0,
    var changedBy: Double = 0.0,
    var alertFrequency: Long = 5000,
    var isPersistent: Boolean = false,
    var isActive: Boolean = true,
    var notes: String = ""
)
