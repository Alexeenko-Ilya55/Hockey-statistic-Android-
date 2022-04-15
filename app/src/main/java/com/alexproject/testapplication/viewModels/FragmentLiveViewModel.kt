package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Response
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.LoadGamesUseCase
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentLiveViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase,
    private val loadGamesUseCase: LoadGamesUseCase
) : ViewModel(), GameFavorites {
    suspend fun loadLiveGames(date: String) =
        loadGamesUseCase.loadLiveGames(date)

    override fun addGameToFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.addToFavorites(game)
    }

    override fun deleteGameFromFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.deleteFromFavoritesFavorites(game)
    }
}