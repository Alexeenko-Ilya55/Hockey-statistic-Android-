package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class AddGameToFavoritesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun addGameToFavorites(gameId: Int) =
        repository.addGameToFavorites(gameId)
}