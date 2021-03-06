package com.alexproject.api.models

import com.alexproject.repository.models.GameEventsDTO


data class GameEvents(
    val `get`: String,
    val response: List<EventsResponse>,
    val results: Int
)

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
    fun mapToDTO(): GameEventsDTO {
        val assists = parseAssists(assists)
        val player = if(players.isEmpty()) null
                     else players[0]
        return GameEventsDTO(
            assists[0],
            assists[1],
            comment,
            game_id,
            minute,
            period,
            player,
            team.mapToDTO(),
            type
        )
    }

    private fun parseAssists(listAssists: List<String>): List<String?>{
        return when(listAssists.size){
            0 -> listOf(null,null)
            1 -> listOf(listAssists[0],null)
            else -> listOf(listAssists[0],listAssists[1])
        }
    }
}