package com.example.cryptoanalytic.common.responses.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("status")
    val status: StatusResponse
)