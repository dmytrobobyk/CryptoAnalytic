package com.example.cryptoanalytic.data.di

import com.example.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.example.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import com.example.cryptoanalytic.data.repository.abs.NewsRepository
import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import com.example.cryptoanalytic.data.repository.impl.CryptocurrencyRepositoryImpl
import com.example.cryptoanalytic.data.repository.impl.NewsRepositoryImpl
import com.example.cryptoanalytic.data.repository.impl.NotificationsRepositoryImpl
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideCryptocurrencyRepository(remoteDataSource: CryptocurrenciesRemoteDataSource, daoAggregator: DaoAggregator): CryptocurrencyRepository = CryptocurrencyRepositoryImpl(remoteDataSource, daoAggregator)

    @Provides
    @Singleton
    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource, daoAggregator: DaoAggregator): NewsRepository = NewsRepositoryImpl(remoteDataSource, daoAggregator)

    @Provides
    @Singleton
    fun provideNotificationsRepository(daoAggregator: DaoAggregator): NotificationsRepository = NotificationsRepositoryImpl(daoAggregator)
}