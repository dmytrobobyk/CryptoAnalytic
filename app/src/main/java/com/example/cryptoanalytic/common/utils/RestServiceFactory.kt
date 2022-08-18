package com.example.cryptoanalytic.common.utils

import com.example.cryptoanalytic.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun <T> createService(clazz: Class<T>, okHttpClient: OkHttpClient, converterFactory: Converter.Factory): T = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .baseUrl(BuildConfig.API_BASE_URL)
    .client(okHttpClient)
    .build()
    .create(clazz)
