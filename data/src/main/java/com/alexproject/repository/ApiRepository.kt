package com.alexproject.repository

import com.alexproject.repository.models.*

interface ApiRepository {
    suspend fun loadCountries(): List<CountryDTO>
    suspend fun loadGamesByDate(date: String): List<GameDTO>
    suspend fun loadGamesH2H(h2h: String): List<GameDTO>
    suspend fun loadGameEvents(gameId: Int): List<GameEventsDTO>
    suspend fun loadStatisticTable(leagueId: Int): List<List<StatisticDTO>>
    suspend fun loadTeam(teamId: Int): List<TeamInfoDTO>
    suspend fun loadTeamGames(teamId: Int): List<GameDTO>
    suspend fun loadGamesForLeague(leagueId: Int): List<GameDTO>
    suspend fun loadAllLeagues(): List<LeagueInfoDTO>
}