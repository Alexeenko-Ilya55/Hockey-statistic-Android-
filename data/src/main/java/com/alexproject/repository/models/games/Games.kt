package com.alexproject.repository.models.games

data class Games(
    val `get`: String,
    val response: List<Response>,
    val results: Int
) {
    fun mapper(): com.alexproject.domain.models.Games {
        return com.alexproject.domain.models.Games(
            errors = emptyList(),
            response = mapperForList(response),
            results = results,
            get = get
        )
    }

    private fun mapperForList(response: List<Response>): List<com.alexproject.domain.models.Response> {
        return response.map {
            it.mapper()
        }
    }
}
