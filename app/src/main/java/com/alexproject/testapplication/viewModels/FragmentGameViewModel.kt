package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.AddTeamToFavoritesUseCase
import com.alexproject.domain.useCases.DeleteTeamFromFavoritesUseCase
import com.alexproject.domain.useCases.LoadGameEventsUseCase
import com.alexproject.domain.useCases.LoadH2HGamesUseCase
import com.alexproject.testapplication.contracts.TeamFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentGameViewModel @Inject constructor(
    private val loadH2HGamesUseCase: LoadH2HGamesUseCase,
    private val loadGameEventsUseCase: LoadGameEventsUseCase,
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase
) : ViewModel(), TeamFavorites {

    fun loadH2HGames(idHomeTeam: String, idAwayTeam: String) =
        loadH2HGamesUseCase.loadH2HGames(idHomeTeam, idAwayTeam)

    fun loadInfoAboutGame(gameId: String) =
        loadGameEventsUseCase.loadInfoAboutGame(gameId)

    override fun addTeamToFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }

    override fun deleteTeamFromFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }
}