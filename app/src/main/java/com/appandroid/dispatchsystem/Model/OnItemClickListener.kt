package com.appandroid.dispatchsystem.Model

import android.view.View

interface OnStoreClickListener
{
    fun onItemClick(store: Store, view: View)
}

interface OnOrderClickListener
{
    fun onItemClick(order: Order, view: View)
}