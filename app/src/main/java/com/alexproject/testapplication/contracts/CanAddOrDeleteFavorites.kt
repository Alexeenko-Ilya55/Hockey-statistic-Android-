package com.alexproject.testapplication.contracts


import com.alexproject.domain.models.Response
import com.alexproject.domain.models.Team
import kotlinx.coroutines.Job

interface GameFavorites {

    fun addGameToFavorites(game: Response): Job
    fun deleteGameFromFavorites(game: Response): Job
}

interface TeamFavorites {

    fun addTeamToFavorites(team: Team): Job
    fun deleteTeamFromFavorites(team: Team): Job
}