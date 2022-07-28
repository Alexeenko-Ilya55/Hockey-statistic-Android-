package com.alexproject.api.models

data class Season(
    val current: Boolean,
    val end: String,
    val season: Int,
    val start: String
)