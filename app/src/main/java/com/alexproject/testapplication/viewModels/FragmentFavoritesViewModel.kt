package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.contracts.GameFavorites
import com.alexproject.testapplication.contracts.TeamFavorites
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
) : ViewModel(), GameFavorites, TeamFavorites{

    override fun addGameToFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addGameToFavoritesUseCase.addGameToFavorites(gameId)
        }

    override fun addTeamToFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }

    override fun deleteGameFromFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
        }

    override fun deleteTeamFromFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }

    suspend fun loadFavoritesGames() = loadFavoritesGamesUseCase.loadFavoritesGames()

    suspend fun loadFavoritesTeams() = loadFavoritesTeamsUseCase.loadFavoritesTeams()
}