package com.example.cryptoanalytic.screens.cryptocurrencies.di

import com.example.cryptoanalytic.common.di.DispatcherIOScope
import com.example.cryptoanalytic.common.utils.createService
import com.example.cryptoanalytic.screens.cryptocurrencies.api.CryptocurrenciesApi
import com.example.cryptoanalytic.screens.cryptocurrencies.datasource.CryptocurrenciesRemoteDataSource
import com.example.cryptoanalytic.screens.cryptocurrencies.repository.CryptocurrenciesLocalRepository
import com.example.cryptoanalytic.screens.cryptocurrencies.repository.CryptocurrenciesRepository
import com.example.cryptoanalytic.screens.cryptocurrencies.viewmodel.CryptocurrenciesViewModel
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Converter
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CryptocurrenciesModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): CryptocurrenciesApi = createService(CryptocurrenciesApi::class.java, okHttpClient, converterFactory)

    @Provides
    @Singleton
    fun provideCryptocurrenciesDataSource(api: CryptocurrenciesApi): CryptocurrenciesRemoteDataSource = CryptocurrenciesRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideCryptocurrenciesRepository(dataSource: CryptocurrenciesRemoteDataSource, daoAggregator: DaoAggregator): CryptocurrenciesRepository = CryptocurrenciesLocalRepository(dataSource, daoAggregator)

    @Provides
    @Singleton
    fun provideCryptocurrenciesViewModel(repository: CryptocurrenciesRepository, @DispatcherIOScope dispatcher: CoroutineDispatcher): CryptocurrenciesViewModel = CryptocurrenciesViewModel(repository, dispatcher)
}