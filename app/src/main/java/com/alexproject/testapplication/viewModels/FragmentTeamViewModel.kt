package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Team
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.LoadGamesUseCase
import com.alexproject.testapplication.contracts.TeamFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeamViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase,
    private val loadGamesUseCase: LoadGamesUseCase
) : ViewModel(), TeamFavorites {

    override fun addTeamToFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.addToFavorites(team)
    }

    override fun deleteTeamFromFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.deleteFromFavoritesFavorites(team)
    }

    suspend fun loadAllGamesForTeam(teamId: String) =
        loadGamesUseCase.loadAllGamesForTeam(teamId)
}