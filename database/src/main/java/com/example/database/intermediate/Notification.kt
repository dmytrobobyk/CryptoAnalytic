package com.example.database.intermediate

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.database.BR
import com.google.gson.annotations.SerializedName

data class Notification (
    var notificationId: Long,
    var cryptocurrencyId: String,
    var cryptocurrencyShortName: String,
    @field:@SerializedName("cryptocurrencyName")
    var _cryptocurrencyName: String? = null,
    var cryptocurrencyMarketRank: Int,
    var cryptocurrencyImageUrl: String,
    var lessThan: Double,
    var moreThan: Double,
    var increasedBy: Double,
    var decreasedBy: Double,
    var changedBy: Double,
    var alertFrequency: Long,
    var isPersistent: Boolean = false,
    var notes: String
) : BaseObservable() {

    var cryptocurrencyName: String? = null
    @Bindable get() = _cryptocurrencyName
    set(value) {
        _cryptocurrencyName = value
        notifyPropertyChanged(BR._all)
    }
}