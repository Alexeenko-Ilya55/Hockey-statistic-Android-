package com.alexproject.api.models

import com.alexproject.repository.models.TeamDTO

data class Team(
    val id: Int,
    val logo: String,
    val name: String
){
    fun mapper() = TeamDTO(id, logo, name,false)
}