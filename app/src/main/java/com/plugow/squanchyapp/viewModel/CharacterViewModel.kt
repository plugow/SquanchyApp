package com.plugow.squanchyapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.squanchyapp.data.local.CharacterResult
import com.plugow.squanchyapp.data.remote.ApiService
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.di.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val service: ApiService): ViewModel() {
    private val mCharacterList = arrayListOf<CharacterResult>()
    private val disposables= CompositeDisposable()
    var characters = MutableLiveData<List<CharacterResult>>()
    private var currentCharacterPage = 1
    private var characterPagesAmount = 1
    var isLoadingRefresh = MutableLiveData<Boolean>(false)
    var isLoading = MutableLiveData<Boolean>(false)
    private val _mainEvent = MutableLiveData<Event<MainEvent>>()
    val mainEvent : LiveData<Event<MainEvent>>
        get() = _mainEvent


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
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

    fun getCharacters(){
        if(characters.value==null){
            isLoadingRefresh.value=true
            loadCharacters(currentCharacterPage)
        }
    }

    fun onRefreshCharacters(){
        currentCharacterPage=1
        mCharacterList.clear()
        loadCharacters(currentCharacterPage)
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