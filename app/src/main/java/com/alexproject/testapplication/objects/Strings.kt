package com.alexproject.testapplication.objects

enum class Status(val get: String) {
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


const val CANCEL_PRESSED = "Cancel pressed"
const val TODAY_TAB_ITEM_INDEX = 3

const val DEFAULT_LEAGUE_ID = 57
const val FAVORITE_LEAGUE_ID = "favoriteLeague"
const val DATA_STORE_NAME = "myDataStore"

const val TODAY_INDEX = 10

enum class Tab(val index: Int) {
    RESULTS(0),
    CALENDAR(1),
    STATISTIC_TABLE(2),
    GAMES(0),
    TEAMS(1),
    EVENTS(0),
    H2H(1)
}

enum class TabsFragmentStatistic(val index: Int) {
    CALENDAR(2),
    STATISTIC(0),
    RESULTS(1)
}

enum class Week(val short: String){
    MONDAY("ПН"),
    TUESDAY("ВТ"),
    WEDNESDAY("СР"),
    THURSDAY("ЧТ"),
    FRIDAY("ПТ"),
    SATURDAY("СБ"),
    SUNDAY("ВС")
}

enum class WeekDays(val index: Int){
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7)
}

