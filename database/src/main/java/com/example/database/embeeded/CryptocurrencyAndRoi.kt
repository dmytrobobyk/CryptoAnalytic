package com.example.database.embeeded

import androidx.room.Embedded
import com.example.database.entity.DbCryptocurrency
import com.example.database.entity.DbRoi

data class CryptocurrencyAndRoi(
    @Embedded
    val dbCryptocurrency: DbCryptocurrency,
    @Embedded
    val dbRoi: DbRoi?
)