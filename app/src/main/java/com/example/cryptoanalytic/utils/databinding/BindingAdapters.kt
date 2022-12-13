package com.example.cryptoanalytic.utils.databinding

import android.content.res.ColorStateList
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.screens.cryptocurrencies.CryptocurrenciesListAdapter
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


object BindingAdapters {
    @BindingAdapter(value = ["cryptocurrencyItems", "clickListener"])
    @JvmStatic
    fun bindCryptocurrencyItems(recyclerView: RecyclerView, cryptocurrencyItems: List<CryptocurrencyResponseItem>, listener: OnItemClickListener<CryptocurrencyResponseItem>) {
        recyclerView.adapter ?: run {
            recyclerView.adapter = CryptocurrenciesListAdapter(listener)
        }
        (recyclerView.adapter as CryptocurrenciesListAdapter).submitList(cryptocurrencyItems.toMutableList())
    }

    @BindingAdapter("time")
    @JvmStatic
    fun bindTimeConversion(textView: TextView, time: String) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        val outputFormat = SimpleDateFormat("dd.MM.yyyy")
        val outputFormat = SimpleDateFormat("HH:mm:ss")
        val date = inputFormat.parse(time)
        val formattedDate: String = outputFormat.format(date)
        textView.text = formattedDate
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @BindingAdapter("changeColor")
    @JvmStatic
    fun bindChangeColor(textView: TextView, change: Double) {
        when {
            change > 0 -> textView.setTextColor(textView.context.getColor(R.color.green))
            change < 0 -> textView.setTextColor(textView.context.getColor(R.color.red))
            change == 0.0 -> textView.setTextColor(textView.context.getColor(R.color.white))
        }
    }

    @BindingAdapter("percentChanged")
    @JvmStatic
    fun bindCryptocurrencyPrice(textView: TextView, percentChange: Double) {
        val df = DecimalFormat("##.##")
        df.roundingMode = RoundingMode.DOWN
        var roundOff = df.format(percentChange)
        val context = textView.context
        if (percentChange > 0) {
            val drawable = context.getDrawable(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_up)?.
            apply {
                    setTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)))
                }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        } else {
            val drawable = context.getDrawable(com.google.android.material.R.drawable.mtrl_ic_arrow_drop_down)?.
            apply {
                setTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)))
            }
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }
        textView.text = "$roundOff%"
    }
}