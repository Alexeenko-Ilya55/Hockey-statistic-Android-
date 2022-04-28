package com.alexproject.testapplication.contracts

import com.alexproject.domain.models.Game
import com.alexproject.domain.models.Team

interface GameClickListener {
    fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean)
    fun itemGameClicked(gameId: Int)
}
interface TeamClickListener {
    fun buttonTeamFavoriteClicked(teamId: Int, isFavorite: Boolean)
    fun itemTeamClicked(teamId: Int)
}


