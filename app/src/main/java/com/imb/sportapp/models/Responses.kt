package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class CountriesResponse(
    @SerializedName("countries")
    var countries: List<Country>?
)

data class SportsResponse(
    @SerializedName("sports")
    var sports: List<Sport>?
)

data class LeaguesResponse(
    @SerializedName("countries")
    var leagues: List<League>?
)

data class TeamsResponse(
    @SerializedName("teams")
    var teams: List<Team>?
)


data class EventsResponse(
    @SerializedName("events")
    var matches: List<Match>?
)

data class ResultsResponse(
    @SerializedName("results")
    var matches: List<Match>?
)

data class PlayersResponse(
    @SerializedName("player")
    var players: List<Player>?
)