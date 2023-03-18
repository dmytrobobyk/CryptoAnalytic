package com.example.cryptoanalytic.common.utils

import com.example.cryptoanalytic.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


fun <T> createService(clazz: Class<T>, okHttpClient: OkHttpClient, converterFactory: Converter.Factory, baseUrl: String = BuildConfig.COIN_GECKO_API_BASE_URL): T = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()
    .create(clazz)
