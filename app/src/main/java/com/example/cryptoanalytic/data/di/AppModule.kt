package com.example.cryptoanalytic.data.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoanalytic.BuildConfig
import com.example.cryptoanalytic.common.utils.createService
import com.example.cryptoanalytic.data.remote.rss.RssXmlConverterFactory
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherDefaultScope
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherMainScope
import com.example.cryptoanalytic.data.di.qualifiers.RetrofitRss
import com.example.cryptoanalytic.data.remote.api.CryptocurrencyService
import com.example.cryptoanalytic.data.remote.api.NewsService
import com.example.database.APP_DATABASE_NAME
import com.example.database.AppDatabase
import com.example.database.DaoAggregator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofitBuilder(converterFactory: Converter.Factory): Retrofit.Builder = Retrofit.Builder().addConverterFactory(converterFactory)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        return okHttpBuilder
            .addInterceptor(Interceptor {chain ->
                var request = chain.request()
                val url = request.url.newBuilder().addQueryParameter("vs_currency","USD").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): CryptocurrencyService = createService(CryptocurrencyService::class.java, okHttpClient, converterFactory)

    @RetrofitRss
    @Provides
    @Singleton
    fun provideRetrofitRss(okHttpClient: OkHttpClient): NewsService = createService(NewsService::class.java, okHttpClient, RssXmlConverterFactory.create(), baseUrl = BuildConfig.NEWS_RSS_FEED)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDaoAggregator(appDatabase: AppDatabase) = DaoAggregator(appDatabase)

    @DispatcherIOScope
    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @DispatcherMainScope
    @Provides
    @Singleton
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @DispatcherDefaultScope
    @Provides
    @Singleton
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

}