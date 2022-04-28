package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFavoritesViewModel @Inject constructor(
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
    private val loadFavoritesGamesUseCase: LoadFavoritesGamesUseCase,
    private val loadFavoritesTeamsUseCase: LoadFavoritesTeamsUseCase
) : ViewModel(), GameFavorites {

    override fun addGameToFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addGameToFavoritesUseCase.addGameToFavorites(gameId)
        }


    fun addTeamToFavorites(teamId: Int, isFavorite: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }


    override fun deleteGameFromFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
        }

    fun deleteTeamFromFavorites(teamId: Int, isFavorite: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }

    suspend fun loadFavoritesGames() = loadFavoritesGamesUseCase.loadFavoritesGames()

    suspend fun loadFavoritesTeams() = loadFavoritesTeamsUseCase.loadFavoritesTeams()
}