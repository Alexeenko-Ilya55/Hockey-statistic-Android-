package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Statistic
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.contracts.GameFavorites
import com.alexproject.testapplication.contracts.TeamFavorites
import com.alexproject.testapplication.models.StatisticTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeamViewModel @Inject constructor(
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val loadTeamGamesUseCase: LoadTeamGamesUseCase,
    private val loadTeamByIdUseCase: LoadTeamByIdUseCase,
    private val loadStatisticsUseCase: LoadStatisticsUseCase,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase
) : ViewModel(), TeamFavorites, GameFavorites {

    override fun addTeamToFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }

    override fun deleteTeamFromFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }

    suspend fun loadTeamById(teamId: Int) = loadTeamByIdUseCase.loadTeamById(teamId)

    suspend fun loadAllGamesForTeam(teamId: Int) =
        loadTeamGamesUseCase.loadAllGamesForTeam(teamId)

    suspend fun loadStatisticsTable(leagueId: Int): Flow<List<StatisticTable>> {
        val statisticList = mutableListOf<StatisticTable>()
        var groupName = ""
        loadStatisticsUseCase.loadStatistic(leagueId).collectLatest { statistic ->
            statistic.first { listStatisticForGroup ->
                listStatisticForGroup.forEach {
                    if (it.nameGroup != groupName) {
                        groupName = it.nameGroup
                        statisticList.add(StatisticTable.Group(groupName))
                    }
                    statisticList.add(mapToStatisticTable(it))
                }
                return@first true
            }
        }
        return flowOf(statisticList as List<StatisticTable>)
    }

    private fun mapToStatisticTable(statistic: Statistic) = StatisticTable.TeamStatistic(
        country = statistic.country,
        description = statistic.description,
        form = statistic.form,
        league = statistic.league,
        points = statistic.points,
        position = statistic.position,
        stage = statistic.stage,
        team = statistic.team,
        winOvertime = statistic.winOvertime,
        win = statistic.win,
        loseOvertime = statistic.loseOvertime,
        lose = statistic.lose,
        againstGoals = statistic.againstGoals,
        forGoals = statistic.forGoals
    )

    override fun addGameToFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        addGameToFavoritesUseCase.addGameToFavorites(gameId)
    }

    override fun deleteGameFromFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
    }
}