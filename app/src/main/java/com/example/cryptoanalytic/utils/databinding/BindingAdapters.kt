package com.example.cryptoanalytic.utils.databinding

import android.content.res.ColorStateList
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.screens.cryptocurrencies.CryptocurrenciesListAdapter
import com.example.cryptoanalytic.screens.notifications.NotificationsListAdapter
import com.example.cryptoanalytic.utils.listeners.OnFavoriteClickListener
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbNotification
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


object BindingAdapters {
    @BindingAdapter(value = ["cryptocurrencyItems", "clickListener", "favoriteListener"])
    @JvmStatic
    fun bindCryptocurrencyItems(recyclerView: RecyclerView, cryptocurrencyItems: List<Cryptocurrency>,
                                listener: OnItemClickListener<Cryptocurrency>,
    favoriteListener: OnFavoriteClickListener<String>) {
        recyclerView.adapter ?: run {
            recyclerView.adapter = CryptocurrenciesListAdapter(listener, favoriteListener)
        }
        (recyclerView.adapter as CryptocurrenciesListAdapter).submitList(cryptocurrencyItems)
    }

    @BindingAdapter(value = ["notificationItems", "clickListener"])
    @JvmStatic
    fun bindNotificationItems(recyclerView: RecyclerView, notificationItems: List<DbNotification>, listener: OnItemClickListener<DbNotification>) {
        recyclerView.adapter ?: run {
            recyclerView.adapter = NotificationsListAdapter(listener)
        }
        (recyclerView.adapter as NotificationsListAdapter).submitList(notificationItems)
    }

    @BindingAdapter("favoriteIcon")
    @JvmStatic
    fun bindFavoriteIcon(imageView: ImageView, isFavorite: Boolean) {
        imageView.setImageResource(
            if (isFavorite) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_border_24
        )
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

//    @BindingAdapter("setSpinnerValue")
//    @JvmStatic
//    fun bindSpinnerValue(spinner: Spinner, value: Any) {
//        if (spinner.adapter != null ) {
//            val position = (spinner.adapter as ArrayAdapter<Any>).getPosition(value)
//            spinner.setSelection(position, false)
//            spinner.tag = position
//        }
//    }


//    @BindingAdapter
//    @JvmStatic
//    fun AppCompatSpinner.setCurrencies(currencies: List<String>?) {
//        adapter = CurrencySpinnerAdapter(context, currencies ?: ArrayList())
//        (adapter as CurrencySpinnerAdapter).setNotifyOnChange(true)
//    }

    @InverseBindingAdapter(attribute = "selectedItem")
    @JvmStatic
    fun AppCompatSpinner.getSelectedItem(): String {
        val item = adapter.getItem(selectedItemPosition)
        return (item as String)
    }

    @BindingAdapter("selectedItem")
    @JvmStatic
    fun AppCompatSpinner.setSelectedCurrency(selected: String?) {
        adapter?.let {
//            val index = (adapter as CurrencySpinnerAdapter).list.indexOf(selected)
            val index = (adapter as ArrayAdapter<String>).getPosition(selected)
            if (index != -1 && selectedItemPosition != index) {
                post {
                    setSelection(index)
                }
            }
        }
    }

    @BindingAdapter("selectedItemAttrChanged")
    @JvmStatic
    fun AppCompatSpinner.setSelectedItemListeners(attrChange: InverseBindingListener) {
        val listener = object : AdapterView.OnItemSelectedListener, View.OnTouchListener {

            var userSelect = false

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                userSelect = true
                return false
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (userSelect) {
                    attrChange.onChange()
                    userSelect = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        setOnTouchListener(listener)
        onItemSelectedListener = listener
    }
}