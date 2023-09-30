package com.example.network.model


import com.google.gson.annotations.SerializedName

data class LocationDetails(
    @SerializedName("created")
    val created: String = "",
    @SerializedName("dimension")
    val dimension: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("residents")
    val residents: List<String> = listOf(),
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val url: String = ""
) {
    private val isIdValid get() = id > 0
    val displayId get() = if (isIdValid) id else ""
}