package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name_en")
    var countryName : String?
)

data class Sport(
    @SerializedName("idSport")
    var sportId : String?,

    @SerializedName("strSport")
    var sportName : String?
)
