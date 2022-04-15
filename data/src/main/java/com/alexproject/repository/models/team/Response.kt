package com.alexproject.repository.models.team

import com.alexproject.repository.models.games.Country

data class Response(
    val arena: Arena,
    val colors: List<String>,
    val country: Country,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean
)