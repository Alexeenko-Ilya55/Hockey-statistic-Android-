package com.alexproject.domain.models

data class League(
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val type: String
)