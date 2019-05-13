package com.plugow.squanchyapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.squanchyapp.data.local.CharacterResult
import com.plugow.squanchyapp.data.local.EpisodeResult
import com.plugow.squanchyapp.data.local.LocationResult
import com.plugow.squanchyapp.data.remote.ApiService
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.di.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

open class MainViewModel @Inject constructor(private val service: ApiService): ViewModel() {
    private val mEpisodesList = arrayListOf<EpisodeResult>()
    private val mLocationList = arrayListOf<LocationResult>()
    private val mCharacterList = arrayListOf<CharacterResult>()
    private val disposables= CompositeDisposable()
    open var episodes = MutableLiveData<List<EpisodeResult>>()
    open var characters = MutableLiveData<List<CharacterResult>>()
    open var locations = MutableLiveData<List<LocationResult>>()
    open var isLoadingRefresh = MutableLiveData<Boolean>(false)
    open var isLoading = MutableLiveData<Boolean>(false)
    private var currentEpisodePage = 1
    private var currentCharacterPage = 1
    private var currentLocationPage = 1
    private var episodePagesAmount = 1
    private var characterPagesAmount = 1
    private var locationPagesAmount = 1
    private val _mainEvent = MutableLiveData<Event<MainEvent>>()
    open val mainEvent : LiveData<Event<MainEvent>>
        get() = _mainEvent


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun loadEpisodes(page:Int){
            service.getEpisodes(page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onSuccess = {
                                it.info?.pages?.let { episodePagesAmount=it }
                                mEpisodesList.addAll(it.results)
                                episodes.value = mEpisodesList
                                loadingOff()
                            },
                            onError = {
                                loadingOff()
                                _mainEvent.value = Event(MainEvent.ERROR)
                            }
                    ).addTo(disposables)
    }


    private fun loadCharacters(page:Int){
        service.getCharacters(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            it.info?.pages?.let { characterPagesAmount=it }
                            mCharacterList.addAll(it.results)
                            characters.value = mCharacterList
                            loadingOff()
                        },
                        onError = {
                            loadingOff()
                            _mainEvent.value = Event(MainEvent.ERROR)
                        }
                ).addTo(disposables)
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

    fun getCharacters(){
        if(characters.value==null){
            isLoadingRefresh.value=true
            loadCharacters(currentCharacterPage)
        }
    }

    fun getLocations(){
        if(locations.value==null){
            isLoadingRefresh.value=true
            loadLocations(currentLocationPage)
        }
    }

    fun getEpisodes(){
        if(episodes.value==null){
            isLoadingRefresh.value=true
            loadEpisodes(currentEpisodePage)
        }
    }

    fun onRefreshEpisodes(){
        currentEpisodePage=1
        mEpisodesList.clear()
        loadEpisodes(currentEpisodePage)
    }

    fun onRefreshCharacters(){
        currentCharacterPage=1
        mCharacterList.clear()
        loadCharacters(currentCharacterPage)
    }

    fun onRefreshLocations(){
        currentLocationPage=1
        mLocationList.clear()
        loadLocations(currentLocationPage)
    }

    open fun onBottomReachedEpisodes(){
        if(isLoading.value == false){
            currentEpisodePage++
            if (currentEpisodePage<=episodePagesAmount){
                isLoading.value = true
                loadEpisodes(currentEpisodePage)
            } else {
                _mainEvent.value = Event(MainEvent.NO_MORE)
            }
        }
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


    fun onBottomReachedCharacters(){
        if(isLoading.value == false){
            currentCharacterPage++
            if (currentCharacterPage<=characterPagesAmount){
                isLoading.value = true
                loadCharacters(currentCharacterPage)
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