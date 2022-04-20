package com.alexproject.api.models

import com.alexproject.repository.models.CountryDTO


data class Country(
    val code: String?,
    val flag: String?,
    val id: Int,
    val name: String
){
    fun mapper() = CountryDTO(code,flag, id, name)
}