package com.alexproject.repository.models.games

import com.alexproject.domain.models.Home

data class Home(
    val id: Int,
    val logo: String,
    val name: String
){
    fun mapper() = Home(id, logo, name)
}