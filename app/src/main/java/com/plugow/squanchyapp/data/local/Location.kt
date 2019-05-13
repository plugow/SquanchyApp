package com.plugow.squanchyapp.data.local


class Locations(
    val info: Info? = null,
    val results: List<LocationResult> = emptyList()
)


data class LocationResult(
    val id: Int=-1,
    val name: String?=null,
    val type: String?=null,
    val dimension: String?=null,
    val residents: List<String> = emptyList(),
    val url: String?=null,
    val created: String?=null
)