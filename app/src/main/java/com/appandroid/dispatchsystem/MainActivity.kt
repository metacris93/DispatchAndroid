package com.appandroid.dispatchsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.Model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnStoreClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var storeAdapter: StoreAdapter

    override fun onItemClick(store: Store, view: View) {
        val intent = Intent(view.context, OrdersActivity::class.java)
        intent.putExtra("store_id", store.id)
        view.context.startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerStoreView)
        storeAdapter = StoreAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = storeAdapter

        val myCall: Call<List<Store>> = IApi.create().getStores()

        myCall.enqueue(object: Callback<List<Store>>{
            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.code() == 200)
                {
                    storeAdapter.setStoreListItems(response.body()!!)
                }
            }
        })
    }
}
