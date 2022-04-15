package com.alexproject.repository.models.games

import com.alexproject.domain.models.Scores

data class Scores(
    val away: Int,
    val home: Int
){
    fun mapper() = Scores(away, home)
}
