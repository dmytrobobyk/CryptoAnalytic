package com.example.cryptoanalytic.screens.notifications.di

import com.example.cryptoanalytic.common.di.DispatcherIOScope
import com.example.cryptoanalytic.screens.notifications.repository.NotificationsLocalRepository
import com.example.cryptoanalytic.screens.notifications.repository.NotificationsRepository
import com.example.cryptoanalytic.screens.notifications.viewmodel.NotificationsViewModel
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NotificationsModule {

    @Provides
    @Singleton
    fun provideNotificationsRepository(daoAggregator: DaoAggregator): NotificationsRepository = NotificationsLocalRepository(daoAggregator)

    @Provides
    @Singleton
    fun provideNotificationsVieModel(repository: NotificationsRepository, @DispatcherIOScope dispatcher: CoroutineDispatcher): NotificationsViewModel = NotificationsViewModel(repository, dispatcher)
}