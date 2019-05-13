package com.plugow.squanchyapp.data.local


class Episodes(
    val info: Info? = null,
    val results: List<EpisodeResult> = emptyList()
)



data class EpisodeResult(
    val id: Int=-1,
    val name: String?=null,
    val air_date: String?=null,
    val episode: String?=null,
    val characters: List<String>?=null,
    val url: String?=null,
    val created: String?=null
)