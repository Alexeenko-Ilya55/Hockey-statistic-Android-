package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadH2HGamesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadH2HGames(idHomeTeam: Int, idAwayTeam: Int) =
        repository.loadH2HGames(idHomeTeam, idAwayTeam)
}