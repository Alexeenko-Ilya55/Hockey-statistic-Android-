package com.alexproject.repository.models.team

data class Team(
    val errors: List<Any>,
    val `get`: String,
    val response: List<Response>,
    val results: Int
)