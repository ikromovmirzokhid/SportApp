package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("idEvent")
    var idEvent: String?,
    @SerializedName("strEvent")
    var matchName: String?,
    @SerializedName("strSport")
    var sportType: String?,
    @SerializedName("strLeague")
    var league: String?,
    @SerializedName("idLeague")
    var idLeague: String?,
    @SerializedName("strSeason")
    var season: String?,
    @SerializedName("strHomeTeam")
    var homeTeam: String?,
    @SerializedName("strAwayTeam")
    var awayTeam: String?,
    @SerializedName("intHomeScore")
    var homeScore: String?,
    @SerializedName("intAwayScore")
    var awayScore: String?,
    @SerializedName("strTimestamp")
    var timestamp: String?,
    @SerializedName("dateEvent")
    var date: String?,
    @SerializedName("strTime")
    var time: String?,
    @SerializedName("strVenue")
    var venue: String?,
    @SerializedName("strThumb")
    var thumb: String?,
    @SerializedName("strPoster")
    var poster: String?,
    @SerializedName("strDescriptionEN")
    var description: String?,
    @SerializedName("strResult")
    var resultDesc: String?
)