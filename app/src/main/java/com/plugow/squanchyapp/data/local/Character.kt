package com.plugow.squanchyapp.data.local


class Characters(
    val info: Info? = null,
    val results: List<CharacterResult> = emptyList()
)

class Origin(
        name: String?=null,
        url: String? = null
)

data class CharacterResult(
    val id: Int=-1,
    val name: String?=null,
    val status: String?=null,
    val type: String?=null,
    val gender: String?=null,
    val origin: Origin?=null,
    val location: Origin?=null,
    val image: String?=null,
    val episode: List<String>?=null,
    val url: String?=null,
    val created: String?=null
)