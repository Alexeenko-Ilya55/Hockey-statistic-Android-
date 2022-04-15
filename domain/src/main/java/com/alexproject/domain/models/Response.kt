package com.alexproject.domain.models

data class Response(
    val country: Country,
    val date: String,
    val events: Boolean,
    val id: Int,
    val league: League,
    val periods: Periods,
    val scores: Scores,
    val status: String,
    val teams: Teams,
    val time: String,
    val timer: Any?,
    val timestamp: Int,
    var bookmarkEnable: Boolean = false
)