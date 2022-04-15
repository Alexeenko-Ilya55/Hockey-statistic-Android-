package com.alexproject.repository.models.gameEvents

data class GameEvents(
    val errors: List<Any>,
    val `get`: String,
    val response: List<Response>,
    val results: Int
)