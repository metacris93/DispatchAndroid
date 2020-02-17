package com.appandroid.dispatchsystem.Model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id")
    var id: Int,
    @SerializedName("lat")
    var lat: Float,
    @SerializedName("lng")
    var lng: Float,
    @SerializedName("description")
    var description: String
)