package com.appandroid.dispatchsystem.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.R
import kotlinx.android.synthetic.main.order_item.view.*

class OrderAdapter(val context: Context, val itemClickListener: OnOrderClickListener) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    var orderList : List<Order> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindView(this.orderList.get(position), itemClickListener)
    }
    fun setOrderListItems(orderList:List<Order>)
    {
        this.orderList = orderList
        notifyDataSetChanged()
    }

    class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        fun bindView(itemOrder: Order?, clickListener: OnOrderClickListener)
        {
            itemOrder?.let {
                with(it)
                {
                    view.txtOrderName.text = it.description
                    view.setOnClickListener {
                        clickListener.onItemClick(this, view)
                    }
                }
            }
        }
    }
    fun updateOrders()
    {

    }
}