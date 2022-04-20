package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexproject.repository.models.CountryDTO

const val TABLE_COUNTRIES = "Countries"

@Entity(tableName = TABLE_COUNTRIES)
data class CountryEntity(
    val code: String?,
    val flag: String?,
    @PrimaryKey
    val id: Int,
    val name: String
) {
    fun toCountry() = CountryDTO(code, flag, id, name)
}
