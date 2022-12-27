package com.example.database

import com.example.database.embeeded.Cryptocurrency

class DaoAggregator(private val database: AppDatabase) {

    // Cryptocurrency list

    suspend fun getCryptocurrencyList(): List<Cryptocurrency>  {
        return database.cryptocurrencyDao().getAll()
    }

    suspend fun saveCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach { cryptocurrency ->
            database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency)
            cryptocurrency.dbRoi?.let { it -> database.roiDao().insert(it) }
        }
    }

    suspend fun deleteCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach {
            database.cryptocurrencyDao().delete(it.dbCryptocurrency)
            it.dbRoi?.let { it1 -> database.roiDao().delete(it1) }
        }
    }

    suspend fun getFavoriteCryptocurrencies(): List<Cryptocurrency> {
        return database.cryptocurrencyDao().getFavorites()
    }

    suspend fun saveCryptocurrencyFavoriteState(cryptocurrency: Cryptocurrency) {
        database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency)
    }




    // Cryptocurrency details


}