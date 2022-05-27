package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class UpdateGameEventsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun updateGamesEvents(gameId: Int) = repository.updateGameEvents(gameId)
}