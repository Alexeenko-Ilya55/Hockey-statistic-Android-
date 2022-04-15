package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadGamesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadGamesByDate(date: String) =
        repository.loadGamesByDate(date)


    suspend fun loadLiveGames(date: String) =
        repository.loadLiveGames(date)


    suspend fun loadAllGamesForTeam(teamId: String) =
        repository.getAllGamesForTeam(teamId)

}