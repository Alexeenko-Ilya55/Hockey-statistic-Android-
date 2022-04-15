package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import com.alexproject.domain.models.Response
import com.alexproject.domain.models.Team
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun addToFavorites(game: Response) =
        repository.addToFavorites(game)


    suspend fun addToFavorites(team: Team) =
        repository.addToFavorites(team)

    suspend fun deleteFromFavoritesFavorites(game: Response) =
        repository.deleteFromFavorites(game)

    suspend fun deleteFromFavoritesFavorites(team: Team) =
        repository.deleteFromFavorites(team)

    suspend fun loadFavoritesGames() =
        repository.loadFavoritesGames()

    suspend fun loadFavoritesTeams() =
        repository.loadFavoritesTeams()
}