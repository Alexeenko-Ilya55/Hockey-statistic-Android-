package com.alexproject.repository.models.games

data class Response(
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
    val timer: Any?,
    val timestamp: Int,
    val timezone: String?,
    val week: Any?
) {
    fun mapper(): com.alexproject.domain.models.Response =
        com.alexproject.domain.models.Response(
            country = country.mapper(),
            date = date ,
            events = events,
            id = id,
            league = league.mapper(),
            periods = periods.mapper(),
            scores = scores.mapper(),
            status = status.short,
            teams = teams.mapper(),
            time = time,
            timer = timer,
            timestamp = timestamp,
        )


}