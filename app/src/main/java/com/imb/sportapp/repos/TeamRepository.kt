package com.imb.sportapp.repos

import com.imb.sportapp.models.Match
import com.imb.sportapp.models.Player
import com.imb.sportapp.models.Resource
import com.imb.sportapp.models.Team

interface TeamRepository {

    suspend fun getTeamsByLeagueId(id: String): Resource<List<Team>>

    suspend fun getTeamsByLeagueName(name: String): Resource<List<Team>>

    suspend fun getTeamById(id: String): Resource<Team>

    suspend fun getTeamNextEvents(id: String): Resource<List<Match>>

    suspend fun getTeamLastEvents(id: String): Resource<List<Match>>

    suspend fun getPlayersByTeamId(id: String): Resource<List<Player>>

}