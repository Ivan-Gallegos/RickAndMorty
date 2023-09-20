package com.example.network.model


import com.google.gson.annotations.SerializedName

data class CharactersPage(
    @SerializedName("info")
    val info: Info = Info(),
    @SerializedName("results")
    val characters: List<Character> = listOf()
)