package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Response
import com.alexproject.domain.models.Team
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFavoritesViewModel @Inject constructor(
    private val useCaseFavorites: FavoritesUseCase
) : ViewModel(), GameFavorites {

    override fun addGameToFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.addToFavorites(game)
    }


    fun addGameToFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.addToFavorites(team)
    }


    override fun deleteGameFromFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.deleteFromFavoritesFavorites(game)
    }

    fun deleteGameFromFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.deleteFromFavoritesFavorites(team)
    }

    fun loadFavoritesGames() = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.loadFavoritesGames()
    }

    fun loadFavoritesTeams() = viewModelScope.launch(Dispatchers.IO) {
        useCaseFavorites.loadFavoritesTeams()
    }
}