
package com.alexproject.testapplication.objects

enum class Status(val get: String){
    GAME_NOT_STARTED("NS"),
    FIRST_PERIOD("P1"),
    SECOND_PERIOD("P2"),
    THIRD_PERIOD("P3"),
    OVER_TIME("OT"),
    PENALTIES_TIME("PT"),
    BREAK_TIME("BT"),
    AWARDED("AW"),
    POSTPONED("POST"),
    GAME_CANCELED("CANC"),
    INTERRUPTED("INTR"),
    ABANDONED("ABD"),
    GAME_FINISHED("FT"),
    AFTER_PENALTIES("AP"),
    AFTER_OVER_TIME("AOT")
}

const val GAME_ID = "gameId"
const val TEAM_ID = "teamId"
const val GAME_NOT_STARTED = "-"
const val LEAGUE = "League:"
const val GOAL = "goal"
const val PENALTY = "penalty"
const val EMPTY_STRING = ""
const val COUNTRY_ID = "countryId"
const val LEAGUE_ID = "leagueId"