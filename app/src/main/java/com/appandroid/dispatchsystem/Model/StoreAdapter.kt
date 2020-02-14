package com.appandroid.dispatchsystem.Model

import android.content.Context
import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.R
import kotlinx.android.synthetic.main.list_item.view.*
import com.appandroid.dispatchsystem.R.layout.list_item

class StoreAdapter(val context: Context) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    var storeList : List<Store> = listOf()
    class StoreViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StoreAdapter.StoreViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(list_item, parent, false)

        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.view.txtName.text = this.storeList.get(position).name
    }

    override fun getItemCount() = storeList.size

    fun setStoreListItems(storeList: List<Store>){
        this.storeList = storeList;
        notifyDataSetChanged()
    }
}