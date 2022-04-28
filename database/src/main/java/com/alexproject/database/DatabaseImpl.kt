package com.alexproject.database

import com.alexproject.database.entities.*
import com.alexproject.repository.Database
import com.alexproject.repository.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DatabaseImpl @Inject constructor(private val dao: Dao) : Database {

    override suspend fun insertGame(games: List<GameDTO>) {
        games.forEach {
            dao.insertGames(toGameEntity(it))
            dao.insertCountry(toCountryEntity(it.country))
            dao.insertTeam(toTeamEntity(it.homeTeam))
            dao.insertTeam(toTeamEntity(it.awayTeam))
            dao.insertLeague(toLeagueEntity(it.league))
        }
    }

    override suspend fun addGameToFavorites(gameId: Int) =
        dao.addGameToFavorites(IsFavoriteGameEntity(gameId))

    override suspend fun insertGameEvents(gameEventsDTO: List<GameEventsDTO>) =
        dao.insertGameEvents(toGameEventsEntity(gameEventsDTO))

    override suspend fun getCountries(): List<CountryDTO> = dao.getCountries().map {
        it.mapToDTO()
    }

    override suspend fun getGamesByDate(date: String) =
        dao.getGamesByDate(date).map { it.map { game -> game.mapToDTO() } }

    override suspend fun deleteGameFromFavorites(gameId: Int) = dao.deleteGameFromFavorites(gameId)

    override suspend fun getFavoritesGames() =
        dao.loadFavoritesGames().map { listGame ->
            listGame.filter { it.favoritesGame != null }.map { game -> game.mapToDTO() }
        }

    override suspend fun getFavoritesTeams() =
        dao.loadFavoritesTeams().map { listTeam ->
            listTeam.filter { it.isFavoriteTeamEntity != null }.map { team -> team.mapToDTO() }
        }

    override suspend fun getLiveGames(date: String): Flow<List<GameDTO>> =
        dao.getGamesByDate(date).map { listGame ->
            listGame.filter {
                it.gamesEntity.status == Status.FIRST_PERIOD.get ||
                        it.gamesEntity.status == Status.SECOND_PERIOD.get ||
                        it.gamesEntity.status == Status.THIRD_PERIOD.get ||
                        it.gamesEntity.status == Status.OVER_TIME.get ||
                        it.gamesEntity.status == Status.PENALTIES_TIME.get ||
                        it.gamesEntity.status == Status.BREAK_TIME.get
            }.map { game -> game.mapToDTO() }
        }

    override suspend fun getGameById(gameId: Int) = dao.getGameById(gameId).map { it.mapToDTO() }

    override suspend fun loadGameEvents(gameId: Int) =
        dao.loadGameEvents(gameId).map { listEvents -> listEvents.map { it.mapToDTO() } }


    override suspend fun deleteTeamFromFavorites(teamId: Int) = dao.deleteTeamToFavorites(teamId)

    override suspend fun getH2HGames(homeTeamId: Int, awayTeamId: Int) =
        dao.getH2HGames(homeTeamId, awayTeamId).map { listGame -> listGame.map { it.mapToDTO() } }

    override suspend fun getTeamGames(teamId: Int): Flow<List<GameDTO>> =
        dao.getTeamGames(teamId).map { listGame -> listGame.map { it.mapToDTO() } }

    override suspend fun getTeamById(teamId: Int): Flow<TeamDTO> =
        dao.getTeamById(teamId).map { it.mapToDTO() }

    override suspend fun getStatistic(leagueId: Int): Flow<List<List<StatisticDTO>>> {
        return emptyFlow()
    }
    // override suspend fun getStatistic(leagueId: Int) = dao.getStatistic(leagueId)
    //     .map { group -> group.map { listStatistic -> listStatistic.map { it.mapToDTO() } } }

    override suspend fun addTeamToFavorites(teamId: Int) =
        dao.addTeamToFavorites(IsFavoriteTeamEntity(teamId))


    private fun toGameEventsEntity(gameEvents: List<GameEventsDTO>) = gameEvents.map {
        GameEventsEntity(
            0,
            it.assistsFirst,
            it.assistsSecond,
            it.comment,
            it.game_id,
            it.minute,
            it.period,
            it.players,
            it.team.id,
            it.type
        )
    }

    private fun toGameEntity(game: GameDTO) = GamesEntity(
        game.id,
        game.country.id,
        game.date,
        game.time,
        game.events,
        game.league.id,
        game.awayScores,
        game.homeScores,
        game.status,
        game.awayTeam.id,
        game.homeTeam.id,
        game.timer,
        game.firstPeriod,
        game.overtime,
        game.penalties,
        game.secondPeriod,
        game.thirdPeriod
    )

    private fun toTeamEntity(team: TeamDTO) =
        TeamEntity(team.id, team.logo, team.name)

    private fun toCountryEntity(country: CountryDTO) =
        CountryEntity(country.code, country.flag, country.id, country.name)

    private fun toLeagueEntity(leagueDTO: LeagueDTO) =
        LeagueEntity(leagueDTO.id, leagueDTO.logo, leagueDTO.name, leagueDTO.type)
}

enum class Status(val get: String) {
    GAME_NOT_STARTED("NS"),
    FIRST_PERIOD("P1"),
    SECOND_PERIOD("P2"),
    THIRD_PERIOD("P3"),
    OVER_TIME("OT"),
    PENALTIES_TIME("PT"),
    BREAK_TIME("BT"),
    AWARDED("AW"),
    POSTPONED("POST"),
    GAME_CANCELED("CANC"),
    INTERRUPTED("INTR"),
    ABANDONED("ABD"),
    GAME_FINISHED("FT"),
    AFTER_PENALTIES("AP"),
    AFTER_OVER_TIME("AOT")
}
