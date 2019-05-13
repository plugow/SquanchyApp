package com.plugow.squanchyapp.di.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plugow.squanchyapp.ui.adapter.BaseAdapter
import com.plugow.squanchyapp.ui.adapter.ScrollListener


@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<T>?) {
    if (recyclerView.adapter is BaseAdapter<*>) {
        items?.let {
            (recyclerView.adapter as BaseAdapter<T>).setData(it)
        }
    }
}

@BindingAdapter("onBottomReached")
fun setScrollListener(recyclerView: RecyclerView, scrollListener: ScrollListener){
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val visibleItemCount  = recyclerView.childCount
            val totalItemCount = (recyclerView.adapter)?.itemCount
            if ((!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) && firstVisibleItemPosition >= 0) {
                scrollListener.onBottomReached()
            }
        }
    })
}

@BindingAdapter("isLoading")
fun setIsLoading(recyclerView: RecyclerView, loading:Boolean){
    (recyclerView.adapter as BaseAdapter<*>).setIsLoading(loading)
    if (loading) recyclerView.scrollToPosition(recyclerView.adapter!!.itemCount -1)
}

