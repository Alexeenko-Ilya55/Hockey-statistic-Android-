package com.alexproject.repository.models.statistic

data class StatisticTable(
    val errors: List<Any>,
    val `get`: String,
    val response: List<List<Response>>,
    val results: Int
)