package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadFavoritesTeamsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadFavoritesTeams() = repository.loadFavoritesTeams()
}