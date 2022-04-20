package com.alexproject.repository.models

import com.alexproject.domain.models.GameEvents

data class GameEventsDTO(
    val assistsFirst: String?,
    val assistsSecond: String?,
    val comment: String?,
    val game_id: Int,
    val minute: String,
    val period: String,
    val players: String,
    val team: TeamDTO,
    val type: String
) {
    fun mapper() = GameEvents(
        assistsFirst,
        assistsSecond,
        comment,
        game_id,
        minute,
        period,
        players,
        team.mapper(),
        type
    )
}