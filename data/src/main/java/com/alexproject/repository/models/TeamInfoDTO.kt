package com.alexproject.repository.models

import com.alexproject.domain.models.TeamInfo

data class TeamInfoDTO(
    val country: CountryDTO,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean,
    val capacityArena: Int,
    val locationArena: String,
    val nameArena: String
) {
    fun mapToTeamInfo() = TeamInfo(
        country.mapToCountry(),
        founded,
        id,
        logo,
        name,
        national,
        capacityArena,
        locationArena,
        nameArena
    )
}



