package com.cryptoanalytic.utils

import com.cryptoanalytic.data.BuildConfig
import com.cryptoanalytic.domain.wrapper.Result
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit


fun <T> createService(clazz: Class<T>, okHttpClient: OkHttpClient, converterFactory: Converter.Factory, baseUrl: String = BuildConfig.COIN_GECKO_API_BASE_URL): T = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()
    .create(clazz)

suspend fun<T> getResponse(request: suspend () -> Response<T>): Result<T> {
    val result = request.invoke()
    return if (result.isSuccessful) {
        Result.Success(result.body())
    } else {
        Result.Error(Result.Error.Code.UNKNOWN, result.errorBody()?.string())
    }
}