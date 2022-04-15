package com.alexproject.repository.models.gameEvents

data class Response(
    val assists: List<String>,
    val comment: Any?,
    val game_id: Int,
    val minute: String,
    val period: String,
    val players: List<String>,
    val team: Team,
    val type: String
)