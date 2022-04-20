package com.alexproject.repository

import com.alexproject.domain.Repository
import com.alexproject.domain.models.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val apiRepository: ApiRepository,
    val database: Database
) : Repository {
    override suspend fun addGameToFavorites(gameId: Int) {
        database.addGametoFavorites(gameId)
    }

    override suspend fun addTeamToFavorites(teamId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGameFromFavorites(gameId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTeamFromFavorites(teamId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavoritesGames() {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavoritesTeams() {
        TODO("Not yet implemented")
    }

    override suspend fun searchTeam(teamName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadStatistics() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGamesForTeam(teamId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadLiveGames(date: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadGamesFromApiToDB(date: String) {
        val games = apiRepository.loadGamesByDate(date)
        database.insertGame(games)
    }

    override suspend fun loadGamesByDate(date: String): Flow<List<Game>> =
        database.getGamesByDate(date).map { it.map { gameDTO -> gameDTO.mapper() } }


    override suspend fun searchGameByTeamName(teamName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun searchGameByLeagueName(leagueName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun searchGameByCountryIndex(countryIndex: String) {
        TODO("Not yet implemented")
    }


}