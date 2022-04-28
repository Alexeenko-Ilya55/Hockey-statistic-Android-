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
    fun mapToStatistic() = Statistic(
        country.mapToCountry(),
        description,
        form,
        nameGroup,
        league.mapToLeague(),
        points,
        position,
        stage,
        team.mapToTeam(),
        winOvertime,
        win,
        loseOvertime,
        lose,
        againstGoals,
        forGoals
    )
}

