package com.imb.sportapp.repos

import com.imb.sportapp.models.Match
import com.imb.sportapp.models.Player
import com.imb.sportapp.models.Resource
import com.imb.sportapp.models.Team
import com.imb.sportapp.network.TeamApi
import javax.inject.Inject

class TeamRepositoryImp @Inject constructor(private val api: TeamApi) : TeamRepository {

    override suspend fun getTeamsByLeagueId(id: String): Resource<List<Team>> {
        return try {
            val response = api.getTeamsByLeagueId(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.teams ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getTeamsByLeagueName(name: String): Resource<List<Team>> {
        return try {
            val response = api.getTeamsByLeagueName(name = name)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.teams ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getTeamById(id: String): Resource<Team> {
        return try {
            val response = api.getTeamById(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    it.teams?.get(0)?.let { team ->
                        Resource.SUCCESS(team)
                    }
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getTeamNextEvents(id: String): Resource<List<Match>> {
        return try {
            val response = api.getTeamNextEvents(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.matches ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getTeamLastEvents(id: String): Resource<List<Match>> {
        return try {
            val response = api.getTeamLastEvents(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.matches ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getPlayersByTeamId(id: String): Resource<List<Player>> {
        return try {
            val response = api.getPlayersByTeamId(id = id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.players ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }
}