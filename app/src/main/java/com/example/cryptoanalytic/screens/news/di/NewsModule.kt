package com.example.cryptoanalytic.screens.news.di

import com.example.cryptoanalytic.BuildConfig
import com.example.cryptoanalytic.common.di.DispatcherIOScope
import com.example.cryptoanalytic.common.utils.RssXmlConverterFactory
import com.example.cryptoanalytic.common.utils.createService
import com.example.cryptoanalytic.screens.news.api.NewsApi
import com.example.cryptoanalytic.screens.news.datasource.NewsRemoteDataSource
import com.example.cryptoanalytic.screens.news.repository.NewsLocalRepository
import com.example.cryptoanalytic.screens.news.repository.NewsRepository
import com.example.cryptoanalytic.screens.news.viewmodel.NewsViewModel
import com.example.database.DaoAggregator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NewsModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): NewsApi = createService(NewsApi::class.java, okHttpClient, RssXmlConverterFactory.create(), baseUrl = BuildConfig.NEWS_RSS_FEED)

    @Provides
    @Singleton
    fun provideNewsDataSource(api: NewsApi): NewsRemoteDataSource = NewsRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideNewsRepository(dataSource: NewsRemoteDataSource, daoAggregator: DaoAggregator): NewsRepository = NewsLocalRepository(dataSource, daoAggregator)

    @Provides
    @Singleton
    fun provideNewsViewModel(repository: NewsRepository, @DispatcherIOScope dispatcher: CoroutineDispatcher): NewsViewModel = NewsViewModel(repository, dispatcher)
}