package com.example.cryptoanalytic.data.di.useCases

import com.example.cryptoanalytic.data.repository.abs.NotificationsRepository
import com.example.cryptoanalytic.domain.notifications.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationsUseCasesModule {

    @Provides
    @Singleton
    fun provideDeleteNotificationUseCase(repository: NotificationsRepository) = DeleteNotificationUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNotificationsUseCase(repository: NotificationsRepository) = GetNotificationsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetNotificationUseCase(repository: NotificationsRepository) = GetNotificationUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveNotificationUseCase(repository: NotificationsRepository) = SaveNotificationUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateNotificationActiveStateUseCase(repository: NotificationsRepository) = UpdateNotificationActiveStateUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateNotificationPersistentStateUseCase(repository: NotificationsRepository) = UpdateNotificationPersistentStateUseCase(repository)
}