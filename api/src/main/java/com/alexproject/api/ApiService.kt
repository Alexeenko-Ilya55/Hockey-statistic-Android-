package com.alexproject.api

import com.alexproject.repository.models.Games
import retrofit2.http.GET
import retrofit2.http.Headers

import retrofit2.http.Path
import retrofit2.http.Query

const val HOST  = "x-rapidapi-host:"
const val KEY  = "x-rapidapi-key:"
const val API_SPORTS  = "v1.hockey.api-sports.io"

interface ApiService {

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET("{endPoint}")
    suspend fun loadGames(
        @Path("endPoint") endPoint: String,
        @Query("date") date: String
    ): Games
}