package com.imb.sportapp.models

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idPlayer")
    var idPlayer: String?,
    @SerializedName("strNationality")
    var nationality: String?,
    @SerializedName("strPlayer")
    var name: String?,
    @SerializedName("strTeam")
    var team: String?,
    @SerializedName("dateBorn")
    var dateBorn: String?,
    @SerializedName("strNumber")
    var number: String?,
    @SerializedName("strPosition")
    var position: String?,
    @SerializedName("strHeight")
    var height: String?,
    @SerializedName("strWeight")
    var weight: String?,
    @SerializedName("strGender")
    var gender: String?,
    @SerializedName("strCutout")
    var photo: String?,
)