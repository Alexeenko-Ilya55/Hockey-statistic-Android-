package com.alexproject.repository

import com.alexproject.domain.models.Game
import com.alexproject.repository.models.CountryDTO
import com.alexproject.repository.models.GameDTO
import com.alexproject.repository.models.GameEventsDTO
import kotlinx.coroutines.flow.Flow

interface Database {
    suspend fun insertGame(games: List<GameDTO>)
    suspend fun insertGameEvents(gameEventsDTO: List<GameEventsDTO>)
    suspend fun getCountries(): List<CountryDTO>
    suspend fun getGamesByDate(date: String): Flow<List<GameDTO>>
    suspend fun addGametoFavorites(gameId: Int)
}