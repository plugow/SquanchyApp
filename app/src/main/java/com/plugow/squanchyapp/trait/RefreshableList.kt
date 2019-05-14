package com.plugow.squanchyapp.trait

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.plugow.squanchyapp.di.util.Event
import com.plugow.squanchyapp.di.util.MainEvent

interface RefreshableList<T> {
    val mItems: ArrayList<T>
    var items:MutableLiveData<List<T>>
    var currentPage:Int
    var pagesAmount:Int
    var isLoadingRefresh:MutableLiveData<Boolean>
    var isLoading:MutableLiveData<Boolean>
    val mEvent:MutableLiveData<Event<MainEvent>>
    val event : LiveData<Event<MainEvent>>
        get() = mEvent

    fun loadItems(page:Int)
    fun getItems(){
        if(items.value==null){
            isLoadingRefresh.value=true
            loadItems(currentPage)
        }
    }

    fun onRefreshItems(){
        currentPage=1
        mItems.clear()
        loadItems(currentPage)
    }

    fun loadingOff(){
        isLoadingRefresh.value=false
        isLoading.value = false
    }

    fun onBottomReached(){
        if(isLoading.value == false){
            currentPage++
            if (currentPage<=pagesAmount){
                isLoading.value = true
                loadItems(currentPage)
            } else {
                mEvent.value = Event(MainEvent.NO_MORE)
            }
        }
    }

}