package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class DeleteTeamFromFavoritesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun deleteTeamFromFavorites(teamId: Int) = repository.deleteTeamFromFavorites(teamId)
}