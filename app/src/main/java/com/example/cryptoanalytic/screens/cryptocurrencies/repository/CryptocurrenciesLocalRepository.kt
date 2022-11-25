package com.example.cryptoanalytic.screens.cryptocurrencies.repository

import com.example.cryptoanalytic.common.Result
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem
import com.example.cryptoanalytic.screens.cryptocurrencies.datasource.CryptocurrenciesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptocurrenciesLocalRepository @Inject constructor(private val dataSource: CryptocurrenciesDataSource): CryptocurrenciesRepository {

    //CoinMarketCap API
//    override suspend fun getLatestCryptocurrencies(): Flow<Result<List<LatestResponse>>> {
//        return flow {
//            emit(Result.Loading)
//            emit(dataSource.getLatestCryptocurrencies())
////            val list = mutableListOf<LatestResponse>()
////            list.add(LatestResponse(1.0, 1.0, "Piston",null, null, null,null, null, null,null, null, null,null, null, null,null))
////            emit(Result.Success(list))
//            emit(Result.Finish)
//        }
//    }

    //CoinGecko API
    override suspend fun getLatestCryptocurrencies(): Flow<Result<List<CoinMarketResponseItem>>> {
        return flow {
            emit(Result.Loading)
            emit(dataSource.getLatestCryptocurrencies())
            emit(Result.Finish)
        }
    }
}