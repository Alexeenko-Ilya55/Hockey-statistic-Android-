package com.alexproject.api.models

import com.alexproject.repository.models.CountryDTO

data class Countries(
    val `get`: String,
    val response: List<Country>,
    val results: Int
){
    fun mapper():List<CountryDTO> = response.map {
        it.mapper()
    }
}