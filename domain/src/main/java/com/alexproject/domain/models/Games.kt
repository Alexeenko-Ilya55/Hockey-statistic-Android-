package com.alexproject.domain.models

data class Games(
    val errors: List<Any>,
    val `get`: String,
    val response: List<Response>,
    val results: Int
)