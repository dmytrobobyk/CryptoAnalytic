package com.example.cryptoanalytic.utils.formatters

import kotlin.math.abs

object NumberFormatter {
    fun formatDouble(
        numberToFormat: Double,
        withPrefix: Boolean = false,
        withPercent: Boolean = false,
        withUnit: Boolean = false
    ): String {
        var number = numberToFormat
        var prefix = ""
        if (withPrefix) {
            if (number != 0.0) {
                prefix = if (number > 0) "+" else "-"
            }
            number = abs(number)
        }
        val percent = if (withPercent) "%" else ""
        var unit = ""
        if (number > 10e9) {
            number /= 10e9
            unit = " Billion"
        } else if (number > 10e6) {
            number /= 10e6
            unit = " Million"
        } else if (number > 1000) {
            number /= 1000
            unit = " Thousand"
        }
        return prefix + String.format("%.2f", number) + percent + unit
    }
}