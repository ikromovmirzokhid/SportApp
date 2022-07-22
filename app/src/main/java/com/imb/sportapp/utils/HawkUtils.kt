package com.imb.sportapp.utils

import com.orhanobut.hawk.Hawk

object HawkUtils {

    var favoriteTeamId: String?
        get() = Hawk.get("favoriteTeamId", "")
        set(value) {
            Hawk.put("favoriteTeamId", value)
        }

    var favoriteTeam: String?
        get() = Hawk.get("favoriteTeam", "")
        set(value) {
            Hawk.put("favoriteTeam", value)
        }
}