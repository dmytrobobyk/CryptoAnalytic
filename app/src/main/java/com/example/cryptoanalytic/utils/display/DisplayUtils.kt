package com.example.cryptoanalytic.utils.display

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue




object DisplayUtils {
    fun Int.toPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

    fun Int.toDp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()

    fun Int.intoDp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
//        context.resources.displayMetrics
        Resources.getSystem().displayMetrics
    ).toInt()
}