package com.alexproject.repository.models.leagues

data class Season(
    val current: Boolean,
    val end: String,
    val season: Int,
    val start: String
)