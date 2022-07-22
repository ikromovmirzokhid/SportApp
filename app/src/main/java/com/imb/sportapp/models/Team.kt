package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    var idTeam: String?,
    @SerializedName("strTeam")
    var teamName: String?,
    @SerializedName("strTeamShort")
    var teamNameShort: String?,
    @SerializedName("strSport")
    var sportType: String?,
    @SerializedName("intFormedYear")
    var formedYear: String?,
    @SerializedName("idLeague")
    var idLeague: String?,
    @SerializedName("strLeague")
    var league: String,
    @SerializedName("strStadium")
    var stadium: String?,
    @SerializedName("strKeywords")
    var keywords: String?,
    @SerializedName("strStadiumThumb")
    var stadiumThumb: String?,
    @SerializedName("strStadiumDescription")
    var stadiumDescription: String?,
    @SerializedName("strStadiumLocation")
    var stadiumLocation: String?,
    @SerializedName("intStadiumCapacity")
    var stadiumCapacity: String?,
    @SerializedName("strWebsite")
    var website: String,
    @SerializedName("strFacebook")
    var facebook: String,
    @SerializedName("strTwitter")
    var twitter: String,
    @SerializedName("strInstagram")
    var instagram: String,
    @SerializedName("strDescriptionEN")
    var description: String,
    @SerializedName("strCountry")
    var country: String?,
    @SerializedName("strTeamBadge")
    var badge: String?,
    @SerializedName("strTeamJersey")
    var jersey: String?,
    @SerializedName("strTeamLogo")
    var logo: String?,
    @SerializedName("strTeamFanart1")
    var art1: String?,
    @SerializedName("strTeamFanart2")
    var art2: String?,
    @SerializedName("strTeamFanart3")
    var art3: String?,
    @SerializedName("strTeamFanart4")
    var art4: String?,
    @SerializedName("strTeamBanner")
    var banner: String?,
    @SerializedName("strGender")
    var gender: String?,
)