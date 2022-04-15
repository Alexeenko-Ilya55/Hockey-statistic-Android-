package com.alexproject.repository.models.games

import com.alexproject.domain.models.Away

data class Away(
    val id: Int,
    val logo: String,
    val name: String
){
    fun mapper() = Away(id, logo, name)
}