package com.alexproject.testapplication.contracts

interface GameClickListener {
    fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean)
    fun itemGameClicked(gameId: Int)
}

interface TeamClickListener {
    fun buttonTeamFavoriteClicked(teamId: Int, isFavorite: Boolean)
    fun itemTeamClicked(teamId: Int)
}


