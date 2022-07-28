package com.alexproject.api.models

import com.alexproject.repository.models.StatisticDTO

data class StatisticTable(
    val errors: List<Any>,
    val response: List<List<StatisticResponse>>,
    val results: Int
) {
    fun mapToDTO(): List<List<StatisticDTO>> = response.map {
        it.map { statisticResponse ->
            statisticResponse.toStatistic()
        }
    }
}

data class StatisticResponse(
    val country: Country,
    val description: String?,
    val form: String?,
    val games: GamesStatistic,
    val goals: Goals,
    val group: Group,
    val league: League,
    val points: Int,
    val position: Int,
    val stage: String,
    val team: Team
) {
    fun toStatistic() = StatisticDTO(
        country.mapToDTO(),
        description,
        form,
        group.name,
        league.mapToDTO(),
        points,
        position,
        stage,
        team.mapToDTO(),
        games.win_overtime.total,
        games.win.total,
        games.lose_overtime.total,
        games.lose.total,
        goals.against,
        goals.`for`
    )
}

data class WinOvertime(
    val percentage: String,
    val total: Int
)

data class Win(
    val percentage: String,
    val total: Int
)

data class LoseOvertime(
    val percentage: String,
    val total: Int
)

data class Lose(
    val percentage: String,
    val total: Int
)

data class Group(
    val name: String
)

data class Goals(
    val against: Int,
    val `for`: Int
)

data class GamesStatistic(
    val lose: Lose,
    val lose_overtime: LoseOvertime,
    val played: Int,
    val win: Win,
    val win_overtime: WinOvertime
)