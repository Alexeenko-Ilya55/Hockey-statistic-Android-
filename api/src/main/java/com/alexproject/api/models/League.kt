package com.alexproject.api.models

import com.alexproject.repository.models.LeagueDTO


data class League(
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val type: String
){
    fun mapToDTO() = LeagueDTO(id, logo, name, type)
}