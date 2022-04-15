package com.alexproject.repository.models.leagues

import com.alexproject.repository.models.games.Country

data class Response(
    val country: Country,
    val id: Int,
    val logo: String,
    val name: String,
    val seasons: List<Season>,
    val type: String
)