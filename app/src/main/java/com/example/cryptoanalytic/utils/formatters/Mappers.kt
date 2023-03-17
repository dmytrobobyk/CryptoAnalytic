package com.example.cryptoanalytic.utils.formatters

import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency
import com.example.database.entity.DbNotification
import com.example.database.entity.DbRoi
import com.example.database.intermediate.CryptoNotification

fun DbNotification.mapToIntermediateDataClass(): CryptoNotification {
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

fun CryptoNotification.mapToDbDataClass(): DbNotification {
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

fun CryptocurrencyResponseItem.mapToIntermediateDataClass(): Cryptocurrency {
    with(this) {
        return Cryptocurrency(
            DbCryptocurrency(id, ath, athChangePercentage, athDate, atl, atlChangePercentage, atlDate,
                circulatingSupply, currentPrice, fullyDilutedValuation, high24h, image, lastUpdated,
                low24h, marketCap, marketCapChange24h, marketCapChangePercentage24h, marketCapRank, maxSupply,
                name, priceChange24h, priceChangePercentage24h, symbol, totalSupply, totalVolume),
                roiResponse?.let { DbRoi(id, roiResponse.currency, roiResponse.percentage, roiResponse.times) }
        )
    }
}
