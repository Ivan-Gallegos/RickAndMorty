package com.example.network

import com.example.network.model.Character
import com.example.network.model.CharactersPage
import com.example.network.model.LocationDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface RMService {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun getCharacterPage(
        @Query("page") page: Int,
        @Query("name") name: String
    ): Response<CharactersPage>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: Int): Response<Character>

    @GET
    suspend fun getLocationDetails(@Url url: String): Response<LocationDetails>
}