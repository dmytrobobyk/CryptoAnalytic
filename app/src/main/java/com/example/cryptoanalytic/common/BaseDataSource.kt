package com.example.cryptoanalytic.common

import com.example.cryptoanalytic.common.responses.base.BaseResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.net.UnknownHostException


open class BaseDataSource {
//    suspend fun <T> getResponse(request: suspend () -> Response<BaseResponse<T>>): Result<BaseResponse<T>> {
//        return try {
//            val result = request.invoke()
//            if (result.isSuccessful) {
//               Result.Success(result.body())
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
    suspend fun <T> getResponse(request: suspend () -> Response<BaseResponse<T>>): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                Result.Success(result.body()?.data)
            } else {
                return try {
                    val genericType = object : TypeToken<BaseResponse<T>>() {}.type
                    val responseError = Gson().fromJson<BaseResponse<T>>(result.errorBody()?.string(), genericType)
                    Result.Error(Result.Error.Code.UNKNOWN, responseError.status.errorMessage)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Result.Error(Result.Error.Code.UNKNOWN, e.message)
                }
            }
        } catch (e: Throwable) {
            when (e) {
//                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION, context.get()?.getString(R.string.something_went_wrong))
                is UnknownHostException -> Result.Error(Result.Error.Code.NO_CONNECTION, "No internet connection")
                else -> Result.Error(Result.Error.Code.UNKNOWN, e.message)
            }
        }
    }

}
