package com.appandroid.dispatchsystem.Model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: Int,
    @SerializedName("lat")
    val lat: Float,
    @SerializedName("lng")
    val lng: Float,
    @SerializedName("name")
    val name: String){

}