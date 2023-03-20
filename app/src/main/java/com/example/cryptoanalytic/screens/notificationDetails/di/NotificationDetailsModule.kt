package com.example.cryptoanalytic.screens.notificationDetails.di

import android.content.Context
import com.example.cryptoanalytic.screens.notificationDetails.repository.NotificationDetailsLocalRepository
import com.example.cryptoanalytic.screens.notificationDetails.repository.NotificationDetailsRepository
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NotificationDetailsModule {

    @Provides
    @Singleton
    fun provideNotificationDetailsRepository(@ApplicationContext context: Context,  daoAggregator: DaoAggregator): NotificationDetailsRepository = NotificationDetailsLocalRepository(context, daoAggregator)
}