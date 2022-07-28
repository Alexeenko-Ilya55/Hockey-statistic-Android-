package com.alexproject.database

import androidx.room.*
import androidx.room.Dao
import com.alexproject.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(gamesEntity: GamesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(teamEntity: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryEntity: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(leagueEntity: LeagueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(listLeagues: List<LeagueInfoEntity>)

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES WHERE date = :date ORDER BY time")
    fun getGamesByDate(date: String): Flow<List<Game>>

    @Query("SELECT * FROM $TABLE_COUNTRIES")
    suspend fun getCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(listCountries: List<CountryEntity>)

    @Query("DELETE FROM $TABLE_GAMES")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameEvents(gameEventsEntity: List<GameEventsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGameToFavorites(isFavoriteGameEntity: IsFavoriteGameEntity)

    @Query("DELETE FROM $TABLE_FAVORITES_GAMES WHERE gameId = :gameId")
    suspend fun deleteGameFromFavorites(gameId: Int)

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES")
    fun loadFavoritesGames(): Flow<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTeamToFavorites(isFavoriteTeamEntity: IsFavoriteTeamEntity)

    @Query("DELETE FROM $TABLE_FAVORITES_TEAM WHERE teamId = :teamId")
    suspend fun deleteTeamToFavorites(teamId: Int)

    @Transaction
    @Query("SELECT * FROM $TABLE_TEAM")
    fun loadFavoritesTeams(): Flow<List<Team>>

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES WHERE id = :gameId")
    fun getGameById(gameId: Int): Flow<Game>

    @Transaction
    @Query("SELECT * FROM $TABLE_GAME_EVENTS WHERE game_id = :gameId")
    fun loadGameEvents(gameId: Int): Flow<List<GameEvents>>

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES WHERE homeTeamId = :homeTeamId AND awayTeamId = :awayTeamId ORDER BY date DESC")
    fun getH2HGames(homeTeamId: Int, awayTeamId: Int): Flow<List<Game>>

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES WHERE homeTeamId = :teamId OR awayTeamId = :teamId")
    fun getTeamGames(teamId: Int): Flow<List<Game>>

    @Transaction
    @Query("SELECT * FROM $TABLE_TEAM WHERE id = :teamId ")
    fun getTeamById(teamId: Int): Flow<Team>

    @Query("SELECT * FROM $TABLE_COUNTRIES WHERE id = :countryId ")
    fun getCountryById(countryId: Int): Flow<CountryEntity>

    @Query("SELECT * FROM $TABLE_LEAGUE_INFO WHERE id = :leagueId ")
    fun getLeagueById(leagueId: Int): Flow<LeagueInfo>

    @Transaction
    @Query("SELECT * FROM $TABLE_LEAGUE_INFO ORDER BY name")
    fun getAllLeagues(): Flow<List<LeagueInfo>>
}



