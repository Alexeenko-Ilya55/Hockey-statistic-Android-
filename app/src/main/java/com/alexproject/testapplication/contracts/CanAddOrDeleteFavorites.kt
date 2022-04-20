package com.alexproject.testapplication.contracts

import kotlinx.coroutines.Job

interface GameFavorites {
    fun addGameToFavorites(gameId: Int): Job
    fun deleteGameFromFavorites(gameId: Int): Job
}

interface TeamFavorites {

    fun addTeamToFavorites(teamId: Int): Job
    fun deleteTeamFromFavorites(teamId: Int): Job
}
