package com.alexproject.api

import retrofit2.http.GET


interface ApiService {

    @GET("/")
    suspend fun get()
}