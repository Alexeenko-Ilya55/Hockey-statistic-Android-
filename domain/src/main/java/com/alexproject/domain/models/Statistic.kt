package com.alexproject.domain.models

data class Statistic(
    val country: Country,
    val description: String?,
    val form: String,
    val nameGroup: String,
    val league: League,
    val points: Int,
    val position: Int,
    val stage: String,
    val team: Team,
    val winOvertime: Int,
    val win: Int,
    val loseOvertime: Int,
    val lose: Int,
    val againstGoals: Int,
    val forGoals: Int
)

