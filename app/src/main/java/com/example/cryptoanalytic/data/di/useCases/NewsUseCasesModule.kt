package com.example.cryptoanalytic.data.di.useCases

import com.example.cryptoanalytic.data.repository.abs.NewsRepository
import com.example.cryptoanalytic.domain.news.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NewsUseCasesModule {

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repository: NewsRepository) = GetNewsUseCase(repository)
}