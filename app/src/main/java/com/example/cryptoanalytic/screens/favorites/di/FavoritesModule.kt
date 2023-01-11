package com.example.cryptoanalytic.screens.favorites.di

import com.example.cryptoanalytic.screens.favorites.repository.FavoritesLocalRepository
import com.example.cryptoanalytic.screens.favorites.repository.FavoritesRepository
import com.example.cryptoanalytic.screens.favorites.viewmodel.FavoritesViewModel
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(daoAggregator: DaoAggregator): FavoritesRepository = FavoritesLocalRepository(daoAggregator)

    @Provides
    @Singleton
    fun provideFavoritesViewModel(repository: FavoritesRepository): FavoritesViewModel = FavoritesViewModel(repository)
}