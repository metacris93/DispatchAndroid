package com.appandroid.dispatchsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.Model.Constants
import com.appandroid.dispatchsystem.Model.IApi
import com.appandroid.dispatchsystem.Model.Store
import com.appandroid.dispatchsystem.Model.StoreAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var storeAdapter: StoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerStoreView)
        storeAdapter = StoreAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = storeAdapter

        val retrofit = GetRetrofitBuilder()
        val service = retrofit.create(IApi::class.java)
        val myCall: Call<List<Store>> = service.getStores()
        myCall.enqueue(object: Callback<List<Store>>{
            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if(response?.body() != null)
                    storeAdapter.setStoreListItems(response.body()!!)
            }
        })
    }
    private fun GetRetrofitBuilder() : Retrofit
    {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.URL)
                .build()
    }
}
