package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadGamesForLeagueUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadGamesForLeague(leagueId: Int) = repository.loadGamesForLeague(leagueId)
}