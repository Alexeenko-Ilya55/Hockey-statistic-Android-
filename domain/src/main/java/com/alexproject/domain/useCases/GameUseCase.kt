package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class GameUseCase @Inject constructor(
    private val repository: Repository
) {

    fun loadInfoAboutGame(gameId: String) {

    }

    fun loadH2HGames(idHomeTeam: String, idAwayTeam: String) {

    }
}