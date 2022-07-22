package com.imb.sportapp.network

import com.imb.sportapp.BuildConfig
import com.imb.sportapp.models.EventsResponse
import com.imb.sportapp.models.PlayersResponse
import com.imb.sportapp.models.ResultsResponse
import com.imb.sportapp.models.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamApi {

    @GET("{API_KEY}/lookup_all_teams.php")
    suspend fun getTeamsByLeagueId(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("id") id: String
    ): Response<TeamsResponse>

    @GET("{API_KEY}/search_all_teams.php")
    suspend fun getTeamsByLeagueName(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("l") name: String
    ): Response<TeamsResponse>

    @GET("{API_KEY}/lookupteam.php")
    suspend fun getTeamById(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("id") id: String
    ): Response<TeamsResponse>

    @GET("{API_KEY}/eventsnext.php")
    suspend fun getTeamNextEvents(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("id") id: String
    ): Response<EventsResponse>

    @GET("{API_KEY}/eventslast.php")
    suspend fun getTeamLastEvents(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("id") id: String
    ): Response<ResultsResponse>

    @GET("{API_KEY}/lookup_all_players.php")
    suspend fun getPlayersByTeamId(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("id") id: String
    ): Response<PlayersResponse>


}