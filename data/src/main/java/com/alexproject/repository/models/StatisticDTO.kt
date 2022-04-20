package com.alexproject.repository.models

import com.alexproject.domain.models.Statistic

data class StatisticDTO(
    val country: CountryDTO,
    val description: String?,
    val form: String,
    val nameGroup: String,
    val league: LeagueDTO,
    val points: Int,
    val position: Int,
    val stage: String,
    val team: TeamDTO,
    val winOvertime: Int,
    val win: Int,
    val loseOvertime: Int,
    val lose: Int,
    val againstGoals: Int,
    val forGoals: Int
) {
    fun mapper() = Statistic(
        country.mapper(),
        description,
        form,
        nameGroup,
        league.mapper(),
        points,
        position,
        stage,
        team.mapper(),
        winOvertime,
        win,
        loseOvertime,
        lose,
        againstGoals,
        forGoals
    )
}

