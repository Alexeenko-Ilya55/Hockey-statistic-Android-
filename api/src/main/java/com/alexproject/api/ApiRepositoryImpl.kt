package com.alexproject.api

import com.alexproject.repository.ApiRepository
import com.alexproject.repository.models.countries.Countries
import com.alexproject.repository.models.gameEvents.GameEvents
import com.alexproject.repository.models.games.Games
import com.alexproject.repository.models.leagues.Leagues
import com.alexproject.repository.models.leaguesGroup.LeaguesGroup
import com.alexproject.repository.models.statistic.StatisticTable
import com.alexproject.repository.models.team.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val GAMES = "games"
const val GAMES_H2H = "games/h2h"

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun loadGamesByDate(date: String): Flow<Games> =
        flow { emit(apiService.loadGames(endPoint = GAMES, date = date, h2h = null)) }

    override suspend fun loadGamesH2H(h2h: String): Flow<Games> =
        flow { emit(apiService.loadGames(endPoint = GAMES_H2H, date = null, h2h = h2h)) }

    override suspend fun loadCountries(): Flow<Countries> =
        flow { emit(apiService.loadCountries()) }

    override suspend fun loadLeagues(): Flow<Leagues> =
        flow { emit(apiService.loadLeagues(null, 2021)) }

    override suspend fun loadLeagues(countryName: String): Flow<Leagues> =
        flow { emit(apiService.loadLeagues(countryName, 2021)) }

    override suspend fun loadLeaguesGroup(leagueId: Int): Flow<LeaguesGroup> =
        flow { emit(apiService.loadLeaguesGroup(leagueId, 2021)) }

    override suspend fun loadGameEvents(gameId: Int): Flow<GameEvents> =
        flow { emit(apiService.loadGameEvents(gameId)) }

    override suspend fun loadStatisticTable(leagueId: Int): Flow<StatisticTable> =
        flow { emit(apiService.loadStatisticTable(leagueId, 2021)) }

    override suspend fun loadTeam(teamId: Int): Flow<Team> =
        flow { emit(apiService.loadTeam(teamId)) }

}