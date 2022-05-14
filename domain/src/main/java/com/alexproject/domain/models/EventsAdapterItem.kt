package com.alexproject.domain.models

sealed class EventsAdapterItem {

    data class GameEvents(
        val assistsFirst: String?,
        val assistsSecond: String?,
        val comment: String?,
        val game_id: Int,
        val minute: String,
        val period: String,
        val players: String?,
        val team: Team,
        val type: String
    ) : EventsAdapterItem()

    data class Period(
        val number: String
    ) : EventsAdapterItem()

}
