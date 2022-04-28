package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadTeamByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadTeamById(teamId: Int) = repository.loadTeamById(teamId)
}