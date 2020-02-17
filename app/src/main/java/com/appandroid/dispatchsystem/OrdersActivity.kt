package com.appandroid.dispatchsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import retrofit2.Call
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.Model.*
import kotlinx.android.synthetic.main.activity_orders.*
import retrofit2.Callback
import retrofit2.Response

class OrdersActivity : AppCompatActivity(), OnOrderClickListener {
    lateinit var recyclerView: RecyclerView
    lateinit var orderAdapter: OrderAdapter
    var storeId: Int = 0

    override fun onItemClick(order: Order, view: View) {
        //TODO
        //GET ORDER DETAIL
        /*val intent = Intent(view.context, CreateOrderActivity::class.java)
        intent.putExtra("store_id", storeId)
        view.context.startActivity(intent)*/
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        intent?.extras?.let {
            storeId = it.getInt("store_id")
        }

        supportActionBar!!.title = "Orders"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerOrderView)
        orderAdapter = OrderAdapter(this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = orderAdapter

        updateOrders()

        fab.setOnClickListener { view ->
            val intent = Intent(view.context, CreateOrderActivity::class.java)
            intent.putExtra("store_id", storeId)
            view.context.startActivity(intent)
        }
    }
    private fun updateOrders()
    {
        val myCall: Call<List<Order>> = IApi.create().getOrders(storeId)

        myCall.enqueue(object: Callback<List<Order>>
        {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.code() == 200)
                {
                    Log.e("Respuesta", response.body().toString())
                    orderAdapter.setOrderListItems(response.body()!!)
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.update_action -> {
                updateOrders()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
