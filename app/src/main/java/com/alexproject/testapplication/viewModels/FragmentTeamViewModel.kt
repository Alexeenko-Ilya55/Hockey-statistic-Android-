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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeamViewModel @Inject constructor(
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val loadTeamGamesUseCase: LoadTeamGamesUseCase,
    private val loadTeamByIdUseCase: LoadTeamByIdUseCase,
    private val loadCountryByIdUseCase: LoadCountryByIdUseCase,
    private val loadLeagueByIdUseCase: LoadLeagueByIdUseCase,
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
        loadStatisticsUseCase.loadStatistic(leagueId)
        val statisticList = mutableListOf<StatisticTable>()
        val statisticTable = loadStatisticsUseCase.loadStatistic(leagueId)
        statisticTable.collect { statistic ->
            statistic.forEach { listStatisticForGroup ->
                statisticList.add(StatisticTable.Group(listStatisticForGroup.first().nameGroup))
                listStatisticForGroup.forEach {
                    statisticList.add(mapToStatisticTable(it))
                }
            }
        }
        return flowOf(statisticList as List<StatisticTable>)
    }

    suspend fun loadLeagueById(leagueId: Int) = loadLeagueByIdUseCase.loadLeagueById(leagueId)

    suspend fun loadCountryById(countryId: Int) = loadCountryByIdUseCase.loadCountryById(countryId)

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