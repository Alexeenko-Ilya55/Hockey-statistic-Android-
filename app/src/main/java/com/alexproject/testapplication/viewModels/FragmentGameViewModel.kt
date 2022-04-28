package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexproject.domain.models.EventsAdapterItem
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.contracts.GameFavorites
import com.alexproject.testapplication.contracts.TeamFavorites
import com.alexproject.testapplication.objects.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentGameViewModel @Inject constructor(
    private val loadH2HGamesUseCase: LoadH2HGamesUseCase,
    private val loadGameEventsUseCase: LoadGameEventsUseCase,
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val loadGameByIdUseCase: LoadGameByIdUseCase,
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase
) : ViewModel(), TeamFavorites, GameFavorites {

    suspend fun loadH2HGames(idHomeTeam: Int, idAwayTeam: Int) =
        loadH2HGamesUseCase.loadH2HGames(idHomeTeam, idAwayTeam)

    suspend fun loadGameEvents(gameId: Int): Flow<List<EventsAdapterItem>> {
        val gameEventsList = mutableListOf<EventsAdapterItem>()
        val events = loadGameEventsUseCase.loadGameEvents(gameId)
        events.first { listEvents ->
            var period = EMPTY_STRING
            listEvents.forEach {
                if (it.period != period) {
                    period = it.period
                    gameEventsList.add(EventsAdapterItem.Period(period.drop(1)))
                }
                gameEventsList.add(it)
            }
            return@first true
        }
        return flowOf(gameEventsList as List<EventsAdapterItem>)
    }


    override fun addTeamToFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            addTeamToFavoritesUseCase.addTeamToFavorites(teamId)
        }

    override fun deleteTeamFromFavorites(teamId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deleteTeamFromFavoritesUseCase.deleteTeamFromFavorites(teamId)
        }

    suspend fun loadGameById(gameId: Int) = loadGameByIdUseCase.loadGameById(gameId)

    override fun addGameToFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        addGameToFavoritesUseCase.addGameToFavorites(gameId)
    }

    override fun deleteGameFromFavorites(gameId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteGameFromFavoritesUseCase.deleteGameFromFavorites(gameId)
    }
}