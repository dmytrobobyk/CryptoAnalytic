package com.cryptoanalytic.data.di

import com.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.cryptoanalytic.data.datasource.impl.CryptocurrenciesRemoteDataSourceImpl
import com.cryptoanalytic.data.datasource.impl.NewsRemoteDataSourceImpl
import com.cryptoanalytic.data.di.qualifiers.RetrofitRss
import com.cryptoanalytic.data.remote.api.CryptocurrencyService
import com.cryptoanalytic.data.remote.api.NewsService
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