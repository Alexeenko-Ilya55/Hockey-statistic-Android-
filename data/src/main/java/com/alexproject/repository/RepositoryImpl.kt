package com.alexproject.repository

import com.alexproject.domain.Repository
import com.alexproject.domain.models.Games
import com.alexproject.domain.models.Response
import com.alexproject.domain.models.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val apiRepository: ApiRepository,
    val database: Database
) : Repository {
    override suspend fun addToFavorites(game: Response) {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(game: Response) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(team: Team) {
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


    override suspend fun loadGamesByDate(date: String): Flow<Games> {
        return apiRepository.loadGamesByDate(date).map {
            it.mapper()
        }
    }

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