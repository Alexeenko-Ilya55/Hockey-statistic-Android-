package com.alexproject.repository

import com.alexproject.repository.models.*

interface ApiRepository {
    suspend fun loadCountries(): List<CountryDTO>
    suspend fun loadGamesByDate(date: String): List<GameDTO>
    suspend fun loadGamesH2H(h2h: String): List<GameDTO>
    suspend fun loadLeagues(countryName: String): List<LeagueInfoDTO>
    suspend fun loadLeagues(): List<LeagueInfoDTO>
  //  suspend fun loadLeaguesGroup(leagueId: Int):
    suspend fun loadGameEvents(gameId: Int): List<GameEventsDTO>
    suspend fun loadStatisticTable(leagueId: Int): List<List<StatisticDTO>>
    suspend fun loadTeam(teamId: Int): List<TeamInfoDTO>
}