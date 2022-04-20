package com.alexproject.domain.models

data class GameEvents(
    val assistsFirst: String?,
    val assistsSecond: String?,
    val comment: String?,
    val game_id: Int,
    val minute: String,
    val period: String,
    val players: String,
    val team: Team,
    val type: String
)