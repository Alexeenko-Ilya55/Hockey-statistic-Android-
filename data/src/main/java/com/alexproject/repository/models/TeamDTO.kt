package com.alexproject.repository.models

import com.alexproject.domain.models.Team

data class TeamDTO(
    val id: Int,
    val logo: String,
    val name: String,
    var isFavorite: Boolean
) {
    fun mapper() = Team(id, logo, name,isFavorite)
}