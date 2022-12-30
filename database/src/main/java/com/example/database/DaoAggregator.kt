package com.example.database

import com.example.database.embeeded.Cryptocurrency
import kotlinx.coroutines.flow.Flow

class DaoAggregator(private val database: AppDatabase) {

    // Cryptocurrency list

    fun getCryptocurrencyList(): Flow<List<Cryptocurrency>> {
        return database.cryptocurrencyDao().getAll()
    }

    fun saveCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach { cryptocurrency ->
            database.cryptocurrencyDao().insert(cryptocurrency.dbCryptocurrency)
            cryptocurrency.dbRoi?.let { it -> database.roiDao().insert(it) }
        }
    }

    fun deleteCryptocurrencyList(cryptocurrencyList: List<Cryptocurrency>) {
        cryptocurrencyList.forEach {
            database.cryptocurrencyDao().delete(it.dbCryptocurrency)
            it.dbRoi?.let { it1 -> database.roiDao().delete(it1) }
        }
    }

    fun getFavoriteCryptocurrencies(): Flow<List<Cryptocurrency>> {
        return database.cryptocurrencyDao().getFavorites()
    }

    fun saveCryptocurrencyFavoriteState(cryptocurrency: Cryptocurrency) {
        database.cryptocurrencyDao().update(cryptocurrency.dbCryptocurrency)
    }




    // Cryptocurrency details


}