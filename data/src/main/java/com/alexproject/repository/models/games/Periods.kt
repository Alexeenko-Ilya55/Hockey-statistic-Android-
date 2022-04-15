package com.alexproject.repository.models.games

import com.alexproject.domain.models.Periods

data class Periods(
    val first: String,
    val overtime: Any?,
    val penalties: Any?,
    val second: String,
    val third: String
) {
    fun mapper() = Periods(first, overtime, penalties, second, third)
}