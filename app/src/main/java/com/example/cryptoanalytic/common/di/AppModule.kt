package com.example.cryptoanalytic.common.di

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.Room
import com.example.cryptoanalytic.MainViewModel
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
    fun provideRetrofitBuilder(converterFactory: Converter.Factory): Retrofit.Builder = Retrofit.Builder().addConverterFactory(converterFactory)

    @Provides
    @Singleton
    fun provideConverterFactory(@Nullable gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDaoAggregator(appDatabase: AppDatabase) = DaoAggregator(appDatabase)

    @Provides
    @Singleton
    fun provideMainViewModel(): MainViewModel = MainViewModel()


}