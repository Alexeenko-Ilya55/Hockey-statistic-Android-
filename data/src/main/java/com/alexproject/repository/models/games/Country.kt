package com.alexproject.repository.models.games

data class Country(
    val code: String?,
    val flag: String?,
    val id: Int?,
    val name: String?
){
    fun mapper(): com.alexproject.domain.models.Country =
        com.alexproject.domain.models.Country(code, flag, id, name)
}