package com.alexproject.testapplication.models

import com.alexproject.domain.models.Country
import com.alexproject.domain.models.League
import com.alexproject.domain.models.Team

sealed class StatisticTable {

    data class TeamStatistic(
        val country: Country,
        val description: String?,
        val form: String,
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
    ) : StatisticTable()

    data class Group(
        val nameGroup: String
    ) : StatisticTable()
}