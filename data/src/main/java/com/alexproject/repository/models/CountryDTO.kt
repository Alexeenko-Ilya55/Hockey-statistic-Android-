package com.alexproject.repository.models

import com.alexproject.domain.models.Country

data class CountryDTO(
    val code: String?,
    val flag: String?,
    val id: Int,
    val name: String
){
    fun mapper() = Country(code, flag, id, name)
}
