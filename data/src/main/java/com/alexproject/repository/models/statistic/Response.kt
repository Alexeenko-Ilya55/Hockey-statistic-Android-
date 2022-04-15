package com.alexproject.repository.models.statistic

import com.alexproject.repository.models.games.Country

data class Response(
    val country: Country,
    val description: String?,
    val form: String,
    val games: Games,
    val goals: Goals,
    val group: Group,
    val league: League,
    val points: Int,
    val position: Int,
    val stage: String,
    val team: Team
)