package com.alexproject.repository

import com.alexproject.repository.models.*
import kotlinx.coroutines.flow.Flow

interface Database {
    suspend fun insertGame(games: List<GameDTO>)
    suspend fun insertGameEvents(gameEventsDTO: List<GameEventsDTO>)
    suspend fun insertLeagues(leagues: List<LeagueInfoDTO>)
    suspend fun insertCountries(countries: List<CountryDTO>)
    suspend fun getCountries(): List<CountryDTO>
    suspend fun getGamesByDate(date: String): Flow<List<GameDTO>>
    suspend fun addGameToFavorites(gameId: Int)
    suspend fun deleteGameFromFavorites(gameId: Int)
    suspend fun getFavoritesGames(): Flow<List<GameDTO>>
    suspend fun getFavoritesTeams(): Flow<List<TeamDTO>>
    suspend fun getLiveGames(date: String): Flow<List<GameDTO>>
    suspend fun getGameById(gameId: Int): Flow<GameDTO>
    suspend fun loadGameEvents(gameId: Int): Flow<List<GameEventsDTO>>
    suspend fun addTeamToFavorites(teamId: Int)
    suspend fun deleteTeamFromFavorites(teamId: Int)
    suspend fun getH2HGames(homeTeamId: Int, awayTeamId: Int): Flow<List<GameDTO>>
    suspend fun getTeamGames(teamId: Int): Flow<List<GameDTO>>
    suspend fun getTeamById(teamId: Int): Flow<TeamDTO>
    suspend fun getStatistic(leagueId: Int): Flow<List<List<StatisticDTO>>>
    suspend fun getCountryById(countryId: Int): Flow<CountryDTO>
    suspend fun getLeagueById(leagueId: Int): Flow<LeagueDTO>
    suspend fun loadAllLeagues(): Flow<List<LeagueInfoDTO>>
}
