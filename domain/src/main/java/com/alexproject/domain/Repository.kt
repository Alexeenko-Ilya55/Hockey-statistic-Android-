package com.alexproject.domain

import com.alexproject.domain.models.Games
import com.alexproject.domain.models.Response
import com.alexproject.domain.models.Team
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addToFavorites(game: Response)
    suspend fun addToFavorites(team: Team)
    suspend fun deleteFromFavorites(game: Response)
    suspend fun deleteFromFavorites(team: Team)
    suspend fun loadFavoritesGames()
    suspend fun loadFavoritesTeams()
    suspend fun searchTeam(teamName: String)
    suspend fun loadStatistics()
    suspend fun getAllGamesForTeam(teamId: String)
    suspend fun loadLiveGames(date: String)
    suspend fun loadGamesByDate(date: String): Flow<Games>
    suspend fun searchGameByTeamName(teamName: String)
    suspend fun searchGameByLeagueName(leagueName: String)
    suspend fun searchGameByCountryIndex(countryIndex: String)
}