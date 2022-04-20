package com.alexproject.database

import com.alexproject.database.entities.*
import com.alexproject.repository.Database
import com.alexproject.repository.models.*
import kotlinx.coroutines.flow.Flow
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

    override suspend fun addGametoFavorites(gameId: Int) = dao.addGameToFavorites(IsFavoriteGameEntity(gameId,true))

    override suspend fun insertGameEvents(gameEventsDTO: List<GameEventsDTO>) =
        dao.insertGameEvents(toGameEventsEntity(gameEventsDTO))

    override suspend fun getCountries(): List<CountryDTO> = dao.getCountries().map {
        it.toCountry()
    }

    override suspend fun getGamesByDate(date: String): Flow<List<GameDTO>> =
        dao.getGamesByDate(date).map { it.map { game -> game.mapper() } }


    private fun toGameEventsEntity(gameEvents: List<GameEventsDTO>) = gameEvents.map {
        GameEventsEntity(
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
        TeamEntity(team.id, team.logo, team.name, team.isFavorite)

    private fun toCountryEntity(country: CountryDTO) =
        CountryEntity(country.code, country.flag, country.id, country.name)

    private fun toLeagueEntity(leagueDTO: LeagueDTO) =
        LeagueEntity(leagueDTO.id, leagueDTO.logo, leagueDTO.name, leagueDTO.type)
}
