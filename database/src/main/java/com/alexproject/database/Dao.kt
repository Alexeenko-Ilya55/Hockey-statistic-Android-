package com.alexproject.database

import androidx.room.*
import androidx.room.Dao
import com.alexproject.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGames(gamesEntity: GamesEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(teamEntity: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryEntity: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(leagueEntity: LeagueEntity)

    @Transaction
    @Query("SELECT * FROM $TABLE_GAMES WHERE date = :date")
    fun getGamesByDate(date: String): Flow<List<Game>>

    @Query("SELECT * FROM $TABLE_COUNTRIES")
    suspend fun getCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(listCountries: List<CountryEntity>)

    @Query("DELETE FROM $TABLE_GAMES")
    suspend fun deleteAll()

    @Query("UPDATE $TABLE_TEAM SET isFavorite =:isFavorite WHERE id = :teamId")
    suspend fun updateTeam(teamId: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameEvents(gameEventsEntity: List<GameEventsEntity>)

    @Query("SELECT * FROM $TABLE_GAME_EVENTS")
    suspend fun getGameEvents(): List<GameEvents>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGameToFavorites(isFavorite: IsFavoriteGameEntity)

    @Query("DELETE FROM $TABLE_FAVORITES_GAMES WHERE gameId = :gameId")
    suspend fun deleteGameFromFavorites(gameId: Int)
}



