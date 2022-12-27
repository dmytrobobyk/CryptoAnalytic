package com.example.database.converters

import androidx.room.TypeConverter

class CollectionsConverter {

    @TypeConverter
    fun fromIntMutableList(items: MutableList<Int>): String = items.joinToString(",")

    @TypeConverter
    fun toIntMutableList(value: String?): MutableList<Int> {
        return if (!value.isNullOrEmpty()) {
            value.split(",").map(String::toInt).toMutableList()
        } else {
            mutableListOf()
        }
    }

    @TypeConverter
    fun fromLongMutableList(items: MutableList<Long>): String = items.joinToString(",")

    @TypeConverter
    fun toLongMutableList(value: String?): MutableList<Long> {
        return if (!value.isNullOrEmpty()) {
            value.split(",").map(String::toLong).toMutableList()
        } else {
            mutableListOf()
        }
    }

    @TypeConverter
    fun fromFloatList(items: MutableList<Float>): String = items.joinToString(",")

    @TypeConverter
    fun toFloatList(value: String?): MutableList<Float> {
        return if (!value.isNullOrEmpty()) {
            value.split(",").map(String::toFloat).toMutableList()
        } else {
            mutableListOf()
        }
    }

    @TypeConverter
    fun fromLongIntMutableMap(items: MutableMap<Long, Int>): String {
        return StringBuffer().let { buffer ->
            items.entries.forEachIndexed { index, entry ->
                buffer.append("%d=%d%s".format(
                    entry.key,
                    entry.value,
                    if (index != items.size - 1) "," else ""
                ))
            }

            buffer.toString()
        }
    }

    @TypeConverter
    fun toLongIntMutableMap(value: String?): MutableMap<Long, Int> {
        return if (!value.isNullOrEmpty()) {
            value
                .split(",")
                .associate {
                    val (key, value) = it.split("=")
                    key.toLong() to value.toInt()
                }
                .toMutableMap()
        } else {
            mutableMapOf()
        }
    }

    @TypeConverter
    fun fromLongMutableSet(items: MutableSet<Long>): String = items.joinToString(",")

    @TypeConverter
    fun toLongMutableSet(value: String?): MutableSet<Long> {
        return if (!value.isNullOrEmpty()) {
            value.split(",").map(String::toLong).toMutableSet()
        } else {
            mutableSetOf()
        }
    }

    @TypeConverter
    fun fromStringMutableList(items: MutableList<String>?): String? {
        return items?.joinToString("stringSeparator")
    }

    @TypeConverter
    fun toStringMutableList(value: String?): MutableList<String> {
        return if (!value.isNullOrEmpty()) {
            value.split("stringSeparator").toMutableList()
        } else {
            mutableListOf()
        }
    }
}