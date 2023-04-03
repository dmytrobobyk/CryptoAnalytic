package com.example.cryptoanalytic.data.di

import com.example.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.example.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.example.cryptoanalytic.data.datasource.impl.CryptocurrenciesRemoteDataSourceImpl
import com.example.cryptoanalytic.data.datasource.impl.NewsRemoteDataSourceImpl
import com.example.cryptoanalytic.data.di.qualifiers.RetrofitRss
import com.example.cryptoanalytic.data.remote.api.CryptocurrencyService
import com.example.cryptoanalytic.data.remote.api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourcesModule {

    @Provides
    @Singleton
    fun provideCryptocurrenciesDataSource(api: CryptocurrencyService): CryptocurrenciesRemoteDataSource = CryptocurrenciesRemoteDataSourceImpl(api)

    @Provides
    @Singleton
    fun provideNewsDataSource(@RetrofitRss api: NewsService): NewsRemoteDataSource = NewsRemoteDataSourceImpl(api)
}