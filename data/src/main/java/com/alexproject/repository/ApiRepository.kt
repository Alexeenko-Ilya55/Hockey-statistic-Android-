package com.alexproject.repository

import com.alexproject.repository.models.countries.Countries
import com.alexproject.repository.models.gameEvents.GameEvents
import com.alexproject.repository.models.games.Games
import com.alexproject.repository.models.leagues.Leagues
import com.alexproject.repository.models.leaguesGroup.LeaguesGroup
import com.alexproject.repository.models.statistic.StatisticTable
import com.alexproject.repository.models.team.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ApiRepository {
    suspend fun loadCountries(): Flow<Countries>
    suspend fun loadGamesByDate(date: String): Flow<Games>
    suspend fun loadGamesH2H(h2h: String): Flow<Games>
    suspend fun loadLeagues(countryName: String): Flow<Leagues>
    suspend fun loadLeagues(): Flow<Leagues>
    suspend fun loadLeaguesGroup(leagueId: Int): Flow<LeaguesGroup>
    suspend fun loadGameEvents(gameId: Int): Flow<GameEvents>
    suspend fun loadStatisticTable(leagueId: Int): Flow<StatisticTable>
    suspend fun loadTeam(teamId: Int): Flow<Team>
}