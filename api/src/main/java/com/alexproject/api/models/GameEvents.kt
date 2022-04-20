package com.alexproject.api.models

import com.alexproject.repository.models.GameEventsDTO


data class GameEvents(
    val `get`: String,
    val response: List<EventsResponse>,
    val results: Int
){
    fun mapper():List<GameEventsDTO> = response.map {
        it.mapper()
    }
}

data class EventsResponse(
    val assists: List<String>,
    val comment: String?,
    val game_id: Int,
    val minute: String,
    val period: String,
    val players: List<String>,
    val team: Team,
    val type: String
) {
    fun mapper() = GameEventsDTO(
        assists.first(),
        assists[1],
        comment,
        game_id,
        minute,
        period,
        players.first(),
        team.mapper(),
        type
    )
}