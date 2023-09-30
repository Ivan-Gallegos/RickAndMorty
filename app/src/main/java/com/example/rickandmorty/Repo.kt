package com.example.rickandmorty

import com.example.network.RMService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Logger.Companion.DEFAULT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repo {

    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor(DEFAULT).apply {
            level = BODY
        })
        .build()

    // Instantiate Retrofit with GsonConverter to deserialize JSON response
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RMService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    // Instantiate RMService using Retrofit
    private val rMService = retrofit.create<RMService>()

    suspend fun getCharactersPage(page: Int, name : String) = rMService.getCharacterPage(page, name)
    suspend fun getCharacterDetails(id: Int) = rMService.getSingleCharacter(id)
    suspend fun getLocationDetails(url: String) = rMService.getLocationDetails(url)

}