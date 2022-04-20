package com.alexproject.domain.models

data class Team(
    val id: Int,
    val logo: String,
    val name: String,
    var isFavorite: Boolean
)