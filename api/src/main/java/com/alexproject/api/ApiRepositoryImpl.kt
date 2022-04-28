package com.alexproject.api

import com.alexproject.repository.ApiRepository
import com.alexproject.repository.models.GameDTO
import javax.inject.Inject

const val GAMES = "games"
const val GAMES_H2H = "games/h2h"
const val TIME_ZONE = "Europe/Minsk"

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun loadGamesByDate(date: String) =
        apiService.loadGames(endPoint = GAMES, date = date, h2h = null, null, null, TIME_ZONE)
            .mapToDTO()

    override suspend fun loadGamesH2H(h2h: String) =
        apiService.loadGames(endPoint = GAMES_H2H, date = null, h2h = h2h, null, null, TIME_ZONE)
            .mapToDTO()

    override suspend fun loadCountries() = apiService.loadCountries().mapToDTO()

    override suspend fun loadLeagues() =
        apiService.loadLeagues(null, 2021).mapToDTO()

    override suspend fun loadLeagues(countryName: String) =
        apiService.loadLeagues(countryName, 2021).mapToDTO()

    suspend fun loadLeaguesGroup(leagueId: Int) =
        apiService.loadLeaguesGroup(leagueId, 2021)

    override suspend fun loadGameEvents(gameId: Int) =
        apiService.loadGameEvents(gameId).response.map { it.mapToDTO() }

    override suspend fun loadStatisticTable(leagueId: Int) =
        apiService.loadStatisticTable(leagueId, 2021).mapToDTO()

    override suspend fun loadTeam(teamId: Int) =
        apiService.loadTeam(teamId).mapToDTO()

    override suspend fun loadTeamGames(teamId: Int): List<GameDTO> =
        apiService.loadGames(GAMES, null, null, teamId, 2021, TIME_ZONE).mapToDTO()

}