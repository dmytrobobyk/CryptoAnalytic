package com.example.database.intermediate

class Notification (
    var notificationId: Long = 0,
    var cryptocurrencyId: String = "",
    var cryptocurrencyShortName: String = "",
    var cryptocurrencyName: String = "",
    var cryptocurrencyMarketRank: Int = 0,
    var cryptocurrencyImageUrl: String = "",
    var lessThan: Double = 0.0,
    var moreThan: Double = 0.0,
    var increasedBy: Double = 0.0,
    var decreasedBy: Double = 0.0,
    var changedBy: Double = 0.0,
    var alertFrequency: Long = 60000,
    var isPersistent: Boolean = false,
    var notes: String = ""
)
