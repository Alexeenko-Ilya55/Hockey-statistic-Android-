package com.alexproject.api.models

import com.alexproject.repository.models.GameDTO

data class Game(
    val `get`: String,
    val response: List<GamesResponse>,
    val results: Int
) {
    fun mapToDTO():List<GameDTO> = response.map {
        it.mapToDTO()
    }
}

data class GamesResponse(
    val country: Country,
    val date: String,
    val events: Boolean,
    val id: Int,
    val league: League,
    val periods: Periods,
    val scores: Scores,
    val status: Status,
    val teams: Teams,
    val time: String,
    val timer: String?,
    val timezone: String?,
) {
    fun mapToDTO() = GameDTO(
        id,
        country.mapToDTO(),
        date.substringBefore("T"),
        time,
        events,
        league.mapToDTO(),
        scores.away,
        scores.home,
        status.short,
        teams.away.mapToDTO(),
        teams.home.mapToDTO(),
        timer,
        periods.first,
        periods.overtime,
        periods.penalties,
        periods.second,
        periods.third,
        isFavorite = false
    )
}

data class Scores(
    val away: Int,
    val home: Int
)

data class Status(
    val short: String
)

data class Teams(
    val away: Team,
    val home: Team
)

data class Periods(
    val first: String,
    val overtime: String?,
    val penalties: String?,
    val second: String,
    val third: String
)
