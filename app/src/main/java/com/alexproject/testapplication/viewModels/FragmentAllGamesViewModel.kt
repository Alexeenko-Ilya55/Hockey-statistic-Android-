package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.useCases.AddGameToFavoritesUseCase
import com.alexproject.domain.useCases.DeleteGameFromFavoritesUseCase
import com.alexproject.domain.useCases.LoadGamesByDateUseCase
import com.alexproject.domain.useCases.UpdateGamesByDateUseCase
import com.alexproject.testapplication.contracts.GameFavorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class FragmentAllGamesViewModel @Inject constructor(
    private val loadGamesByDateUseCase: LoadGamesByDateUseCase,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
    private val updateGamesByDateUseCase: UpdateGamesByDateUseCase
) : ViewModel(), GameFavorites {

    override fun addGameToFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addGameToFavoritesUseCase.addGameToFavorites(gameId)
        }

    override fun deleteGameFromFavorites(gameId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
        }

    suspend fun loadGamesByDate(date: String) = loadGamesByDateUseCase.loadGamesByDate(date)

    fun updateGames(tabDate: List<LocalDate>) = viewModelScope.launch(Dispatchers.IO) {
        tabDate.forEach {
            updateGamesByDateUseCase.updateGamesByDateUseCase(it.toString())
        }
    }
}