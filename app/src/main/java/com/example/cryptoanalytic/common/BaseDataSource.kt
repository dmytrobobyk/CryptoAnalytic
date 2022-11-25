package com.example.cryptoanalytic.common

import retrofit2.Response


open class BaseDataSource {
    //CoinMarketCap API
//    suspend fun <T> getResponse(request: suspend () -> Response<BaseResponse<T>>): Result<T> {
//        return try {
//            val result = request.invoke()
//            if (result.isSuccessful) {
//                Result.Success(result.body()?.data)
//            } else {
//                return try {
//                    val genericType = object : TypeToken<BaseResponse<T>>() {}.type
//                    val responseError = Gson().fromJson<BaseResponse<T>>(result.errorBody()?.string(), genericType)
//                    Result.Error(Result.Error.Code.UNKNOWN, responseError.status.errorMessage)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Result.Error(Result.Error.Code.UNKNOWN, e.message)
//                }
//            }
//        } catch (e: Throwable) {
//            when (e) {
////                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION, context.get()?.getString(R.string.something_went_wrong))
//                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION, "No internet connection")
//                else -> Result.Error(Result.Error.Code.UNKNOWN, e.message)
//            }
//        }
//    }

    //CoinGecko API
    suspend fun<T> getResponse(request: suspend () -> Response<T>): Result<T> {
        val result = request.invoke()
        return if (result.isSuccessful) {
            Result.Success(result.body())
        } else {
            Result.Error(Result.Error.Code.UNKNOWN, result.errorBody()?.string())
        }
    }

}
