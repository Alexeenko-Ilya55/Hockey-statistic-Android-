package com.alexproject.repository

import com.alexproject.domain.Repository
import com.alexproject.domain.models.EventsAdapterItem
import com.alexproject.domain.models.Game
import com.alexproject.domain.models.Team
import com.alexproject.repository.models.GameDTO
import com.alexproject.repository.models.GameEventsDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val apiRepository: ApiRepository,
    val database: Database
) : Repository {

    override suspend fun addGameToFavorites(gameId: Int) =
        database.addGameToFavorites(gameId)


    override suspend fun addTeamToFavorites(teamId: Int) =
        database.addTeamToFavorites(teamId)

    override suspend fun deleteGameFromFavorites(gameId: Int) =
        database.deleteGameFromFavorites(gameId)


    override suspend fun deleteTeamFromFavorites(teamId: Int) =
        database.deleteTeamFromFavorites(teamId)

    override suspend fun loadFavoritesGames(): Flow<List<Game>> =
        database.getFavoritesGames().map { it.map { gameDTO -> gameDTO.mapToGame() } }

    override suspend fun loadFavoritesTeams() = database.getFavoritesTeams().map {
        it.map { teamDTO -> teamDTO.mapToTeam() }
    }

    override suspend fun searchTeam(teamName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadStatistics(leagueId: Int) = database.getStatistic(leagueId)
        .map { group -> group.map { listStatistic -> listStatistic.map { it.mapToStatistic() } } }

    override suspend fun loadAllGamesForTeam(teamId: Int): Flow<List<Game>> {
        val games = apiRepository.loadTeamGames(teamId)
        database.insertGame(games)
        return database.getTeamGames(teamId).map { listGame -> listGame.map { it.mapToGame() } }
    }

    override suspend fun loadLiveGames(date: String): Flow<List<Game>> =
        database.getLiveGames(date).map { it.map { gameDTO -> gameDTO.mapToGame() } }

    override suspend fun loadGamesFromApiToDB(date: String) {
        val games = apiRepository.loadGamesByDate(date)
        database.insertGame(games)
    }

    override suspend fun loadGamesByDate(date: String): Flow<List<Game>> =
        database.getGamesByDate(date).map { it.map { gameDTO -> gameDTO.mapToGame() } }

    override suspend fun searchGameByTeamName(teamName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun searchGameByLeagueName(leagueName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun searchGameByCountryIndex(countryIndex: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadGameById(gameId: Int) =
        database.getGameById(gameId).map { it.mapToGame() }

    override suspend fun loadGameEvents(gameId: Int): Flow<List<EventsAdapterItem.GameEvents>> {
        val events = database.loadGameEvents(gameId)
        if (events.first() == emptyList<GameEventsDTO>()) {
            database.insertGameEvents(apiRepository.loadGameEvents(gameId))
        }
        return events.map {
            it.map { gameEventsDTO -> gameEventsDTO.mapToGameEvents() }
        }
    }

    override suspend fun loadH2HGames(homeTeamId: Int, awayTeamId: Int): Flow<List<Game>> {
        val gamesH2h = database.getH2HGames(homeTeamId, awayTeamId)
        if (gamesH2h.first().size == 1){
            database.insertGame(apiRepository.loadGamesH2H("$homeTeamId-$awayTeamId"))
        }
        return gamesH2h.map { listGameDTO -> listGameDTO.map { it.mapToGame() } }
    }

    override suspend fun loadTeamById(teamId: Int): Flow<Team> =
        database.getTeamById(teamId).map { it.mapToTeam() }
}