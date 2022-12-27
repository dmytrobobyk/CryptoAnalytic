package com.example.cryptoanalytic.screens.cryptocurrencyDetails.di

import com.example.cryptoanalytic.common.utils.createService
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.api.CryptocurrencyDetailsApi
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.datasource.CryptocurrencyDetailsDataSource
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository.CryptocurrencyDetailsLocalRepository
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.repository.CryptocurrencyDetailsRepository
import com.example.cryptoanalytic.screens.cryptocurrencyDetails.viewmodel.CryptocurrencyDetailsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CryptocurrencyDetailsModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): CryptocurrencyDetailsApi = createService(
        CryptocurrencyDetailsApi::class.java, okHttpClient, converterFactory)

    @Provides
    @Singleton
    fun provideCryptocurrenciesDataSource(api: CryptocurrencyDetailsApi): CryptocurrencyDetailsDataSource = CryptocurrencyDetailsDataSource(api)

    @Provides
    @Singleton
    fun provideCryptocurrenciesRepository(dataSource: CryptocurrencyDetailsDataSource): CryptocurrencyDetailsRepository = CryptocurrencyDetailsLocalRepository(dataSource)

//    @Provides
//    @Singleton
//    fun provideCryptocurrenciesViewModel(repository: CryptocurrencyDetailsRepository): CryptocurrencyDetailsViewModel = CryptocurrencyDetailsViewModel(repository)
}