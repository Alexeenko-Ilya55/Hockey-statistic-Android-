package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class AddTeamToFavoritesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun addTeamToFavorites(teamId: Int) =
        repository.addTeamToFavorites(teamId)
}