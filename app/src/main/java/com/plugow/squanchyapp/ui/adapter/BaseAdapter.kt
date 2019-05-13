package com.plugow.squanchyapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView


const val TYPE_LOADING = 0
const val TYPE_NORMAL = 1

abstract class BaseAdapter<T>(private val createLoadingItem: () -> T) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    var isLoading:Boolean=false
    protected var values: ArrayList<T> = arrayListOf()

    fun setData(items: List<T>) {
        values =items as ArrayList<T>
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            if (position == values.size- 1) TYPE_LOADING else TYPE_NORMAL
        } else {
            TYPE_NORMAL
        }
    }

    private fun remove(loadingItem: T) {
        val position = values.indexOf(loadingItem)
        if (position > -1) {
            values.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun addLoading() {
        values.add(createLoadingItem())
        notifyItemInserted(values.size-1)
    }

    fun setIsLoading(loading:Boolean){
        if (isLoading!=loading){
            isLoading=loading
            if (isLoading){
                addLoading()
            } else{
                remove(createLoadingItem())
            }
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

}