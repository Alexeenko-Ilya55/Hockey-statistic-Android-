package com.alexproject.api

import com.alexproject.repository.ApiRepository
import com.alexproject.repository.models.GameDTO
import com.alexproject.repository.models.StatisticDTO
import java.time.LocalDate
import javax.inject.Inject

const val GAMES = "games"
const val GAMES_H2H = "games/h2h"
const val TIME_ZONE = "Europe/Minsk"
const val OFF_SEASON_MONTH_NUMBER = 7

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService) : ApiRepository {

    override suspend fun loadGamesByDate(date: String) =
        apiService.loadGames(endPoint = GAMES, date = date, h2h = null, null, null, null, TIME_ZONE)
            .mapToDTO()

    override suspend fun loadGamesH2H(h2h: String) =
        apiService.loadGames(
            endPoint = GAMES_H2H,
            date = null,
            h2h = h2h,
            null,
            null,
            null,
            TIME_ZONE
        ).mapToDTO()


    override suspend fun loadCountries() = apiService.loadCountries().mapToDTO()

    override suspend fun loadGameEvents(gameId: Int) =
        apiService.loadGameEvents(gameId).response.map { it.mapToDTO() }

    override suspend fun loadStatisticTable(leagueId: Int): List<List<StatisticDTO>> =
        apiService.loadStatisticTable(leagueId, currentSeason()).mapToDTO()

    override suspend fun loadTeam(teamId: Int) =
        apiService.loadTeam(teamId).mapToDTO()

    override suspend fun loadTeamGames(teamId: Int): List<GameDTO> =
        apiService.loadGames(GAMES, null, null, teamId, currentSeason(), null, TIME_ZONE).mapToDTO()

    override suspend fun loadGamesForLeague(leagueId: Int): List<GameDTO> =
        apiService.loadGames(
            endPoint = GAMES,
            date = null,
            h2h = null,
            null,
            currentSeason(),
            leagueId,
            TIME_ZONE
        ).mapToDTO()

    override suspend fun loadAllLeagues() = apiService.loadLeagues(null, currentSeason()).mapToDTO()

    private fun currentSeason(): Int {
        val localDate = LocalDate.now()
        return if (localDate.monthValue < OFF_SEASON_MONTH_NUMBER)
            localDate.year - 1
        else
            localDate.year
    }
}