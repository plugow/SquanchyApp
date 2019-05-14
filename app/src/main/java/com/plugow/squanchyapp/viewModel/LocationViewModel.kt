package com.plugow.squanchyapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.squanchyapp.data.local.LocationResult
import com.plugow.squanchyapp.data.remote.ApiService
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.di.util.Event
import com.plugow.squanchyapp.trait.RefreshableList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val service: ApiService): ViewModel(), RefreshableList<LocationResult> {
    override val mEvent: MutableLiveData<Event<MainEvent>> =  MutableLiveData()
    override val mItems: ArrayList<LocationResult> = arrayListOf()
    override var items: MutableLiveData<List<LocationResult>> = MutableLiveData()
    override var currentPage: Int = 1
    override var pagesAmount: Int = 1
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val disposables= CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    override fun loadItems(page:Int){
        service.getLocations(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            it.info?.pages?.let { pagesAmount=it }
                            mItems.addAll(it.results)
                            items.value = mItems
                            loadingOff()
                        },
                        onError = {
                            loadingOff()
                            mEvent.value = Event(MainEvent.ERROR)
                        }
                ).addTo(disposables)
    }


}