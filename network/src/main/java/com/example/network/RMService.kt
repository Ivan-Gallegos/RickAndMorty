package com.example.network

import com.example.network.model.Character
import com.example.network.model.CharactersPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RMService {
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun getCharacterPage(@Query("page") page: Int): Response<CharactersPage>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: Int): Response<Character>
}