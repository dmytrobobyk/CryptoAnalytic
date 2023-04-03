package com.example.cryptoanalytic.data.di.useCases

import com.example.cryptoanalytic.data.repository.abs.CryptocurrencyRepository
import com.example.cryptoanalytic.domain.cryptocurrencies.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CryptocurrencyUseCasesModule {

    @Provides
    @Singleton
    fun provideGetCryptocurrenciesUseCase(repository: CryptocurrencyRepository) = GetCryptocurrenciesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCryptocurrencyInfoUseCase(repository: CryptocurrencyRepository) = GetCryptocurrencyInfoUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCryptocurrencyMarketDataUseCase(repository: CryptocurrencyRepository) = GetCryptocurrencyMarketDataUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoritesCryptocurrenciesUseCase(repository: CryptocurrencyRepository) = GetFavoritesCryptocurrenciesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetHistoryOfPriceForDateRangeUseCase(repository: CryptocurrencyRepository) = GetHistoryOfPriceForDateRangeUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveCryptocurrencyFromFavoriteUseCase(repository: CryptocurrencyRepository) = RemoveCryptocurrencyFromFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveFavoriteCryptocurrencyStateUseCase(repository: CryptocurrencyRepository) = SaveFavoriteCryptocurrencyStateUseCase(repository)

}