package com.alexproject.repository.models.games

import com.alexproject.domain.models.League

data class League(
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val type: String
){
    fun mapper() = League(id, logo, name, season, type)
}