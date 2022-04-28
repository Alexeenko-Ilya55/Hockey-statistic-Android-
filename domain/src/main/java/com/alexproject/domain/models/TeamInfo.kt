package com.alexproject.domain.models

data class TeamInfo(
    val country: Country,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean,
    val capacityArena: Int,
    val locationArena: String,
    val nameArena: String
)



