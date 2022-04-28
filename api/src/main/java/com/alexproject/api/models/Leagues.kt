package com.alexproject.api.models

import com.alexproject.repository.models.LeagueInfoDTO

data class Leagues(
    val errors: List<Any>,
    val response: List<LeagueResponse>,
    val results: Int
){
    fun mapToDTO():List<LeagueInfoDTO> = response.map {
        it.toLeagueInfo()
    }
}

data class LeagueResponse(
    val country: Country,
    val id: Int,
    val logo: String,
    val name: String,
    val seasons: List<Season>,
    val type: String
) {
    fun toLeagueInfo() = LeagueInfoDTO(country.mapToDTO(), id, logo, name, type)
}