package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class UpdateGamesByDateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun updateGamesByDateUseCase(date: String) = repository.updateGamesByDate(date)
}