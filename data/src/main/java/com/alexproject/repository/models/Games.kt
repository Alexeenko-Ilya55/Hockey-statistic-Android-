package com.alexproject.repository.models

data class Games(
    val errors: List<Any>,
    val `get`: String,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)

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
    val timer: Any,
    val timestamp: Int,
    val timezone: String,
    val week: Any
)
data class Away(
    val id: Int,
    val logo: String,
    val name: String
)

data class Country(
    val code: String,
    val flag: String,
    val id: Int,
    val name: String
)

data class Home(
    val id: Int,
    val logo: String,
    val name: String
)

data class League(
    val id: Int,
    val logo: String,
    val name: String,
    val season: Int,
    val type: String
)
data class Parameters(
    val h2h: String
)

data class Periods(
    val first: String,
    val overtime: Any,
    val penalties: Any,
    val second: String,
    val third: String
)

data class Scores(
    val away: Int,
    val home: Int
)

data class Status(
    val long: String,
    val short: String
)

data class Teams(
    val away: Away,
    val home: Home
)
