package com.alexproject.repository.models

import com.alexproject.domain.models.Game


data class GameDTO(
    val id: Int,
    val country: CountryDTO,
    val date: String,
    val time: String,
    val events: Boolean,
    val league: LeagueDTO,
    val awayScores: Int?,
    val homeScores: Int?,
    val status: String,
    val awayTeam: TeamDTO,
    val homeTeam: TeamDTO,
    val timer: String?,
    val firstPeriod: String?,
    val overtime: String?,
    val penalties: String?,
    val secondPeriod: String?,
    val thirdPeriod: String?,
    var isFavorite:Boolean
) {
    fun mapToGame() = Game(
        id,
        country.mapToCountry(),
        date,
        time,
        events,
        league.mapToLeague(),
        awayScores,
        homeScores,
        status,
        awayTeam.mapToTeam(),
        homeTeam.mapToTeam(),
        timer,
        firstPeriod,
        overtime,
        penalties,
        secondPeriod,
        thirdPeriod,
        isFavorite
    )
}
