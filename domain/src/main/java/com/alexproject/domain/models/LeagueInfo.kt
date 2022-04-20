package com.alexproject.domain.models


data class LeagueInfo(
    val country: Country,
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
)