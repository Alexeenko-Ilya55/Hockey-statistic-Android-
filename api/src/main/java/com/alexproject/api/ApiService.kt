package com.alexproject.api

import com.alexproject.api.models.*
import com.alexproject.repository.objects.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET("{$END_POINT}")
    suspend fun loadGames(
        @Path(END_POINT) endPoint: String,
        @Query("date") date: String?,
        @Query("h2h") h2h: String?,
        @Query("team") teamId: Int?,
        @Query("season") season: Int?,
        @Query("league") leagueId: Int?,
        @Query("timezone") timeZone: String
    ): Game

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET(COUNTRIES)
    suspend fun loadCountries(): Countries

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET(LEAGUES)
    suspend fun loadLeagues(
        @Query(COUNTRY) countryName: String?,
        @Query(SEASON) season: Int?
    ): Leagues

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET(GAME_EVENTS)
    suspend fun loadGameEvents(
        @Query(GAME) gameId: Int
    ): GameEvents

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET(STANDINGS)
    suspend fun loadStatisticTable(
        @Query(LEAGUE) leagueId: Int,
        @Query(SEASON) season: Int
    ): StatisticTable

    @Headers(
        "$HOST $API_SPORTS",
        "$KEY ${BuildConfig.API_KEY}"
    )
    @GET(TEAMS)
    suspend fun loadTeam(
        @Query(TEAM_ID) teamId: Int
    ): TeamInfo

}