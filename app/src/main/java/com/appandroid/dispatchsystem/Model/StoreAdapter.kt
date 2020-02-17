package com.appandroid.dispatchsystem.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appandroid.dispatchsystem.R
import kotlinx.android.synthetic.main.store_item.view.*

class StoreAdapter(val context: Context, val itemClickListener: OnStoreClickListener) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {
    var storeList : List<Store> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StoreAdapter.StoreViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.store_item, parent, false)

        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        //holder.view.txtStoreName.text = this.storeList.get(position).name
        holder.bindView(this.storeList.get(position), itemClickListener)
    }

    override fun getItemCount() : Int
    {
        return storeList.size
    }

    fun setStoreListItems(storeList: List<Store>){
        this.storeList = storeList
        notifyDataSetChanged()
    }

    class StoreViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        fun bindView(itemStore: Store?, clickListener: OnStoreClickListener)
        {
            itemStore?.let {
                with(it)
                {
                    view.txtStoreName.text = it.name
                    view.setOnClickListener {
                        clickListener.onItemClick(this, view)
                    }
                }
            }
        }
    }
}