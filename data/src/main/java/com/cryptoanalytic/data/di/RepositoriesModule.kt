package com.cryptoanalytic.data.di

import com.cryptoanalytic.data.datasource.abs.CryptocurrenciesRemoteDataSource
import com.cryptoanalytic.data.datasource.abs.NewsRemoteDataSource
import com.cryptoanalytic.data.repository.CryptocurrencyRepositoryImpl
import com.cryptoanalytic.data.repository.NewsRepositoryImpl
import com.cryptoanalytic.data.repository.NotificationsRepositoryImpl
import com.example.database.DaoAggregator
import com.cryptoanalytic.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@InstallIn(SingletonComponent::class)
//@Module
//object RepositoriesModule {
//
//    @Provides
//    @Singleton
//    fun provideCryptocurrencyRepository(remoteDataSource: CryptocurrenciesRemoteDataSource, daoAggregator: DaoAggregator): CryptocurrencyRepository = CryptocurrencyRepositoryImpl(remoteDataSource, daoAggregator)
//
//    @Provides
//    @Singleton
//    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource, daoAggregator: DaoAggregator): NewsRepository = NewsRepositoryImpl(remoteDataSource, daoAggregator)
//
//    @Provides
//    @Singleton
//    fun provideNotificationsRepository(daoAggregator: DaoAggregator): NotificationsRepository = NotificationsRepositoryImpl(daoAggregator)
//}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun provideCryptocurrencyRepository(cryptocurrencyRepositoryImpl: CryptocurrencyRepositoryImpl): CryptocurrencyRepository

    @Binds
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun provideNotificationsRepository(notificationsRepositoryImpl: NotificationsRepositoryImpl): NotificationsRepository
}