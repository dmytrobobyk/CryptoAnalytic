package com.cryptoanalytic.data.di

import com.cryptoanalytic.data.repository.CryptocurrencyRepositoryImpl
import com.cryptoanalytic.data.repository.NewsRepositoryImpl
import com.cryptoanalytic.data.repository.NotificationsRepositoryImpl
import com.cryptoanalytic.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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