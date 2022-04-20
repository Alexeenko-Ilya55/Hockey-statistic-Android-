package com.alexproject.api.models

import com.alexproject.repository.models.GameDTO

data class Game(
    val `get`: String,
    val response: List<GamesResponse>,
    val results: Int
) {
    fun mapper():List<GameDTO> = response.map {
        it.mapper()
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
    fun mapper() = GameDTO(
        id,
        country.mapper(),
        date.substringBefore("T"),
        time,
        events,
        league.mapper(),
        scores.away,
        scores.home,
        status.short,
        teams.away.mapper(),
        teams.home.mapper(),
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
