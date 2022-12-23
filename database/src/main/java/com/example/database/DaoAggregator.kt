package com.example.database

import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbRoi

class DaoAggregator(private val database: AppDatabase) {

    // Cryptocurrency list

    suspend fun getCryptocurrencyList(): List<Cryptocurrency>  {
        return database.cryptocurrencyDao().getAll()
    }

    suspend fun saveCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach {
            database.cryptocurrencyDao().insert(it.dbCryptocurrency)
            database.roiDao().insert(it.dbRoi)
        }
    }

    suspend fun deleteCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach {
            database.cryptocurrencyDao().delete(it.dbCryptocurrency)
        }
    }




    // Cryptocurrency details


}