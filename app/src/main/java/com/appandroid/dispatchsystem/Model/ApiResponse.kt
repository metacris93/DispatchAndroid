package com.appandroid.dispatchsystem.Model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") val status: Int = 0,
    @SerializedName("message") val message: String = ""
)