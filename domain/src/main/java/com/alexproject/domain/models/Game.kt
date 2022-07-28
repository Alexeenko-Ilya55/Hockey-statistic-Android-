package com.alexproject.domain.models


data class Game(
    val id: Int,
    val country: Country,
    val date: String,
    val time: String,
    val events: Boolean,
    val league: League,
    val awayScores: Int?,
    val homeScores: Int?,
    val status: String,
    val awayTeam: Team,
    val homeTeam: Team,
    val timer: String?,
    val firstPeriod: String?,
    val overtime: String?,
    val penalties: String?,
    val secondPeriod: String?,
    val thirdPeriod: String?,
    var isFavorite: Boolean
)
