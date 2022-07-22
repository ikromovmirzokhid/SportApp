package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class League (
    @SerializedName("idLeague")
    var idLeague : String?,
    @SerializedName("strSport")
    var typeLeague : String?,
    @SerializedName("strLeague")
    var nameLeague : String?,
    @SerializedName("strWebsite")
    var website : String?,
    @SerializedName("strFacebook")
    var facebookPage : String?,
    @SerializedName("strInstagram")
    var instagramPage : String?,
    @SerializedName("strTwitter")
    var twitterPage : String?,
    @SerializedName("strYoutube")
    var youtubePage : String?,
    @SerializedName("strDescriptionEN")
    var description : String?,
    @SerializedName("strFanart1")
    var art1 : String?,
    @SerializedName("strFanart2")
    var art2 : String?,
    @SerializedName("strFanart3")
    var art3 : String?,
    @SerializedName("strFanart4")
    var art4 : String?,
    @SerializedName("strBadge")
    var badge : String?,
    @SerializedName("strLogo")
    var logo : String?
)