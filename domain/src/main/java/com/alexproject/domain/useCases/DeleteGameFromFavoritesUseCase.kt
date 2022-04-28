package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class DeleteGameFromFavoritesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun deleteGameFromFavorites(gameId: Int) = repository.deleteGameFromFavorites(gameId)
}