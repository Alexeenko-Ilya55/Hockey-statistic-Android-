package com.alexproject.domain

import com.alexproject.domain.models.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addGameToFavorites(gameId: Int)
    suspend fun addTeamToFavorites(teamId: Int)
    suspend fun deleteGameFromFavorites(gameId: Int)
    suspend fun deleteTeamFromFavorites(teamId: Int)
    suspend fun loadFavoritesGames(): Flow<List<Game>>
    suspend fun loadFavoritesTeams(): Flow<List<Team>>
    suspend fun loadStatistics(leagueId: Int): Flow<List<List<Statistic>>>
    suspend fun loadAllGamesForTeam(teamId: Int): Flow<List<Game>>
    suspend fun loadLiveGames(date: String): Flow<List<Game>>
    suspend fun loadGamesByDate(date: String): Flow<List<Game>>
    suspend fun loadGamesFromApiToDB(date: String)
    suspend fun loadGameById(gameId: Int): Flow<Game>
    suspend fun loadGameEvents(gameId: Int): Flow<List<EventsAdapterItem.GameEvents>>
    suspend fun loadH2HGames(homeTeamId: Int, awayTeamId: Int): Flow<List<Game>>
    suspend fun loadTeamById(teamId: Int): Flow<Team>
    suspend fun loadCountryById(countryId: Int): Flow<Country>
    suspend fun loadLeagueById(leagueId: Int): Flow<League>
    suspend fun loadGamesForLeague(leagueId: Int): Flow<List<Game>>
    suspend fun loadAllLeagues(): Flow<List<League>>
    suspend fun updateGamesByDate(date: String)
    suspend fun updateGameEvents(gameId: Int)
    suspend fun updateAllLeagues()
}