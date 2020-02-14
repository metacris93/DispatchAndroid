package com.appandroid.dispatchsystem.Model

import retrofit2.Call
import retrofit2.http.GET

public interface IApi {
    // GET /api/stores
    @GET("stores")
    fun getStores():Call<List<Store>>
}