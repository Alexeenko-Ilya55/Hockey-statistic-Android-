package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadFavoritesGamesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadFavoritesGames() = repository.loadFavoritesGames()
}