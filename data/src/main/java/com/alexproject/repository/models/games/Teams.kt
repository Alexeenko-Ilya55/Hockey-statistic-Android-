package com.alexproject.repository.models.games

import com.alexproject.domain.models.Teams

data class Teams(
    val away: Away,
    val home: Home
) {
    fun mapper() = Teams(away.mapper(), home.mapper())
}