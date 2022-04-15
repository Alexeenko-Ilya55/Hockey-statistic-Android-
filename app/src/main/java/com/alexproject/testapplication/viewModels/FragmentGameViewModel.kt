package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Team
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.GameUseCase
import com.alexproject.testapplication.contracts.TeamFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentGameViewModel @Inject constructor(
    private val gameUseCase: GameUseCase,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel(), TeamFavorites {

    fun loadH2HGames(idHomeTeam: String, idAwayTeam: String) =
        gameUseCase.loadH2HGames(idHomeTeam, idAwayTeam)

    fun loadInfoAboutGame(gameId: String) =
        gameUseCase.loadInfoAboutGame(gameId)

    override fun addTeamToFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO){
        favoritesUseCase.addToFavorites(team)
    }

    override fun deleteTeamFromFavorites(team: Team) = viewModelScope.launch(Dispatchers.IO){
        favoritesUseCase.deleteFromFavoritesFavorites(team)
    }
}