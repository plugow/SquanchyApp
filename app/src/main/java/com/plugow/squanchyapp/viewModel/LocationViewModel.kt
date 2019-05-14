package com.plugow.squanchyapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.squanchyapp.data.local.LocationResult
import com.plugow.squanchyapp.data.remote.ApiService
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.di.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val service: ApiService): ViewModel() {
    private val mLocationList = arrayListOf<LocationResult>()
    private val disposables= CompositeDisposable()
    var locations = MutableLiveData<List<LocationResult>>()
    var isLoadingRefresh = MutableLiveData<Boolean>(false)
    var isLoading = MutableLiveData<Boolean>(false)
    private var currentLocationPage = 1
    private var locationPagesAmount = 1
    private val _mainEvent = MutableLiveData<Event<MainEvent>>()
    val mainEvent : LiveData<Event<MainEvent>>
        get() = _mainEvent


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    private fun loadLocations(page:Int){
        service.getLocations(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            it.info?.pages?.let { locationPagesAmount=it }
                            mLocationList.addAll(it.results)
                            locations.value = mLocationList
                            loadingOff()
                        },
                        onError = {
                            loadingOff()
                            _mainEvent.value = Event(MainEvent.ERROR)
                        }
                ).addTo(disposables)
    }


    fun getLocations(){
        if(locations.value==null){
            isLoadingRefresh.value=true
            loadLocations(currentLocationPage)
        }
    }

    fun onRefreshLocations(){
        currentLocationPage=1
        mLocationList.clear()
        loadLocations(currentLocationPage)
    }


    fun onBottomReachedLocations(){
        if(isLoading.value == false){
            currentLocationPage++
            if (currentLocationPage<=locationPagesAmount){
                isLoading.value = true
                loadLocations(currentLocationPage)
            } else {
                _mainEvent.value = Event(MainEvent.NO_MORE)
            }
        }
    }


    fun loadingOff(){
        isLoadingRefresh.value=false
        isLoading.value = false
    }

}