package com.appandroid.dispatchsystem.Model

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

public interface IApi {
    // GET /api/stores
    @GET("stores")
    fun getStores():Call<List<Store>>

    @GET("orders/{id}")
    fun getOrders(@Path("id") id: Int):Call<List<Order>>

    @POST("neworder")
    fun createOrder(@Body request: RequestBody) : Call<ApiResponse>
    companion object{
        fun create() : IApi
        {
            val retrofit = Retrofit.Builder()
                           .addConverterFactory(GsonConverterFactory.create())
                           .baseUrl(Constants.URL)
                           .build()
            return retrofit.create(IApi::class.java)
        }
    }
}