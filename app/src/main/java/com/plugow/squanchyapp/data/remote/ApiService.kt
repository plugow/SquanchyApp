package com.plugow.squanchyapp.data.remote

import com.plugow.squanchyapp.data.local.Characters
import com.plugow.squanchyapp.data.local.Episodes
import com.plugow.squanchyapp.data.local.Locations
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("episode")
    fun getEpisodes(@Query("page") page:Int): Single<Episodes>

    @GET("location")
    fun getLocations(@Query("page") page:Int): Single<Locations>

    @GET("character")
    fun getCharacters(@Query("page") page:Int): Single<Characters>
}