package com.example.rickandmorty

import com.example.network.RMService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repo {

    // Instantiate Retrofit with GsonConverter to deserialize JSON response
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RMService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Instantiate RMService using Retrofit
    private val rMService = retrofit.create<RMService>()

    suspend fun getCharactersPage(page: Int) = rMService.getCharacterPage(page)

}