package com.alexproject.repository.models

import com.alexproject.domain.models.Team

data class TeamDTO(
    val id: Int,
    val logo: String,
    val name: String,
    var isFavorite: Boolean
) {
    fun mapToTeam() = Team(id, logo, name,isFavorite)
}