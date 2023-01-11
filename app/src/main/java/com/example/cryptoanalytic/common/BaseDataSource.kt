package com.example.cryptoanalytic.common

import com.example.database.wrapper.Result
import retrofit2.Response


open class BaseDataSource {
    suspend fun<T> getResponse(request: suspend () -> Response<T>): Result<T> {
        val result = request.invoke()
        return if (result.isSuccessful) {
            Result.Success(result.body())
        } else {
            Result.Error(Result.Error.Code.UNKNOWN, result.errorBody()?.string())
        }
    }

}
