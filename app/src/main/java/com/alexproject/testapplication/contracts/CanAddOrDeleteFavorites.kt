package com.alexproject.testapplication.contracts

import com.alexproject.domain.models.Game
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface GameFavorites {
    fun addGameToFavorites(gameId: Int): Job
    fun deleteGameFromFavorites(gameId: Int): Job
}

interface TeamFavorites {
    fun addTeamToFavorites(teamId: Int): Job
    fun deleteTeamFromFavorites(teamId: Int): Job
}
