package com.alexproject.api

import com.alexproject.repository.ApiRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

const val GAMES = "games"
const val GAMES_H2H = "games/h2h"
const val TIME_ZONE = "Europe/Minsk"

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun loadGamesByDate(date: String) =
        apiService.loadGames(endPoint = GAMES, date = date, h2h = null, TIME_ZONE).mapper()

    override suspend fun loadGamesH2H(h2h: String) =
        apiService.loadGames(endPoint = GAMES_H2H, date = null, h2h = h2h, TIME_ZONE).mapper()

    override suspend fun loadCountries() = apiService.loadCountries().mapper()

    override suspend fun loadLeagues() =
        apiService.loadLeagues(null, 2021).mapper()

    override suspend fun loadLeagues(countryName: String) =
        apiService.loadLeagues(countryName, 2021).mapper()

    suspend fun loadLeaguesGroup(leagueId: Int) =
        apiService.loadLeaguesGroup(leagueId, 2021)

    override suspend fun loadGameEvents(gameId: Int) =
        apiService.loadGameEvents(gameId).mapper()

    override suspend fun loadStatisticTable(leagueId: Int) =
        apiService.loadStatisticTable(leagueId, 2021).mapper()

    override suspend fun loadTeam(teamId: Int) =
        apiService.loadTeam(teamId).mapper()

}