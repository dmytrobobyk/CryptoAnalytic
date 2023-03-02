package com.example.database.intermediate

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import androidx.databinding.library.baseAdapters.BR

class Notification (
    var notificationId: Long = 0,
    var cryptocurrencyId: String = "",
//    @field:SerializedName("cryptocurrencyShortName")
//    var _cryptocurrencyShortName: String = "",
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
) : BaseObservable() {

//    var cryptocurrencyShortName: String
//    @Bindable get() = _cryptocurrencyShortName
//    set(value) {
//        _cryptocurrencyShortName = value
//        notifyPropertyChanged(BR.cryptocurrencyShortName)
//    }
}