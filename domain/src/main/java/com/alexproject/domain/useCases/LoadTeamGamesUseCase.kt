package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadTeamGamesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadAllGamesForTeam(teamId: Int) = repository.loadAllGamesForTeam(teamId)
}