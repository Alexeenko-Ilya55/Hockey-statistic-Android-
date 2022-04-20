package com.alexproject.api.models

import com.alexproject.repository.models.TeamInfoDTO

data class TeamInfo(
    val errors: List<Any>,
    val response: List<TeamResponse>,
    val results: Int
){
    fun mapper():List<TeamInfoDTO> = response.map {
        it.toTeamInfoDTO()
    }
}

data class TeamResponse(
    val arena: Arena,
    val colors: List<String>,
    val country: Country,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean
) {
    fun toTeamInfoDTO() = TeamInfoDTO(
        country.mapper(),
        founded,
        id,
        logo,
        name,
        national,
        arena.capacity,
        arena.location,
        arena.name
    )
}

data class Arena(
    val capacity: Int,
    val location: String,
    val name: String
)
