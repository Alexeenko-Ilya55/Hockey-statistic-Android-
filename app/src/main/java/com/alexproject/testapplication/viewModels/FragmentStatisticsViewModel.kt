package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Statistic
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.models.StatisticTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentStatisticsViewModel @Inject constructor(
    private val loadStatisticsUseCase: LoadStatisticsUseCase,
    private val loadLeagueByIdUseCase: LoadLeagueByIdUseCase,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
    private val loadGamesForLeagueUseCase: LoadGamesForLeagueUseCase,
    private val loadAllLeaguesUseCase: LoadAllLeaguesUseCase
) : ViewModel() {

    suspend fun loadStatistic(leagueId: Int): Flow<List<StatisticTable>> {
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

    suspend fun loadLeagueById(leagueId: Int) = loadLeagueByIdUseCase.loadLeagueById(leagueId)

    suspend fun loadAllGamesForLeague(leagueId: Int) =
        loadGamesForLeagueUseCase.loadGamesForLeague(leagueId)

    suspend fun loadAllLeagues() = loadAllLeaguesUseCase.loadAllLeagues()

    fun addGameToFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        addGameToFavoritesUseCase.addGameToFavorites(gameId)
    }

    fun deleteGameFromFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
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
}