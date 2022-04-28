package com.alexproject.repository.models

import com.alexproject.domain.models.LeagueInfo


data class LeagueInfoDTO(
    val country: CountryDTO,
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
){
    fun mapToLeagueInfo() = LeagueInfo(country.mapToCountry(), id, logo, name, type)
}