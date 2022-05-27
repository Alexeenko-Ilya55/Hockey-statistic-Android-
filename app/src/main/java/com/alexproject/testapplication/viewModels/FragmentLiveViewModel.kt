package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.AddGameToFavoritesUseCase
import com.alexproject.domain.useCases.DeleteGameFromFavoritesUseCase
import com.alexproject.domain.useCases.LoadLiveGamesUseCase
import com.alexproject.domain.useCases.UpdateGamesByDateUseCase
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentLiveViewModel @Inject constructor(
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
    private val loadLiveGamesUseCase: LoadLiveGamesUseCase,
    private val updateGamesByDateUseCase: UpdateGamesByDateUseCase
) : ViewModel(), GameFavorites {
    suspend fun loadLiveGames(date: String) =
        loadLiveGamesUseCase.loadLiveGames(date)

    override fun addGameToFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addGameToFavoritesUseCase.addGameToFavorites(gameId)
        }

    override fun deleteGameFromFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
        }

    suspend fun updateLiveGames(date: String) =
        updateGamesByDateUseCase.updateGamesByDateUseCase(date)
}