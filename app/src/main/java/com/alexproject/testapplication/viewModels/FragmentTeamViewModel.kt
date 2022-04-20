package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.AddTeamToFavoritesUseCase
import com.alexproject.domain.useCases.DeleteTeamFromFavoritesUseCase
import com.alexproject.domain.useCases.LoadTeamGamesUseCase
import com.alexproject.testapplication.contracts.TeamFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeamViewModel @Inject constructor(
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val loadTeamGamesUseCase: LoadTeamGamesUseCase
) : ViewModel(), TeamFavorites {

    override fun addTeamToFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }

    override fun deleteTeamFromFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }

    suspend fun loadAllGamesForTeam(teamId: String) =
        loadTeamGamesUseCase.loadAllGamesForTeam(teamId)
}