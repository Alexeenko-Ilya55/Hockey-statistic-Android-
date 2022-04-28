package com.alexproject.repository.models

import com.alexproject.domain.models.League

data class LeagueDTO(
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
) {
    fun mapToLeague() = League(id, logo, name, type)
}
