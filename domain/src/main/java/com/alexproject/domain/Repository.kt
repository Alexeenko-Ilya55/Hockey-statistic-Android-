package com.alexproject.domain

import com.alexproject.domain.models.Game
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addGameToFavorites(gameId: Int)
    suspend fun addTeamToFavorites(teamId: Int)
    suspend fun deleteGameFromFavorites(gameId: Int)
    suspend fun deleteTeamFromFavorites(teamId: Int)
    suspend fun loadFavoritesGames()
    suspend fun loadFavoritesTeams()
    suspend fun searchTeam(teamName: String)
    suspend fun loadStatistics()
    suspend fun getAllGamesForTeam(teamId: String)
    suspend fun loadLiveGames(date: String)
    suspend fun loadGamesByDate(date: String): Flow<List<Game>>
    suspend fun searchGameByTeamName(teamName: String)
    suspend fun searchGameByLeagueName(leagueName: String)
    suspend fun searchGameByCountryIndex(countryIndex: String)
    suspend fun loadGamesFromApiToDB(date: String)
}