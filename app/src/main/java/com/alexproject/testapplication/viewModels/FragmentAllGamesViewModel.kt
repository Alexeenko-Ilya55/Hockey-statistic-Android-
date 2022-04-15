package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.Games
import com.alexproject.domain.models.Response
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.LoadGamesUseCase
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentAllGamesViewModel @Inject constructor(
    private val loadGamesUseCase: LoadGamesUseCase,
    private val favoritesUseCase: FavoritesUseCase
) : ViewModel(), GameFavorites {

    override fun addGameToFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.addToFavorites(game)
    }


    override fun deleteGameFromFavorites(game: Response) = viewModelScope.launch(Dispatchers.IO) {
        favoritesUseCase.deleteFromFavoritesFavorites(game)
    }

    suspend fun loadGamesByDate(date: String): Flow<Games> =
        loadGamesUseCase.loadGamesByDate(date)

}