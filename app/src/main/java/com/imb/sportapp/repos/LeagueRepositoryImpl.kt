package com.imb.sportapp.repos

import com.imb.sportapp.models.League
import com.imb.sportapp.models.Resource
import com.imb.sportapp.network.LeagueApi
import javax.inject.Inject

class LeagueRepositoryImpl @Inject constructor(private val api: LeagueApi) : LeagueRepository {

    override suspend fun getAllCountries(): Resource<List<String>> {
        return try {
            val response = api.getAllCountries()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.countries?.map { it.countryName ?: "" } ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getAllSportTypes(): Resource<List<String>> {
        return try {
            val response = api.getAllSports()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.sports?.map { it.sportName ?: "" } ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getAllLeaguesByCountry(countryName: String): Resource<List<League>> {
        return try {
            val response =
                api.getLeaguesByCountry(countryName = countryName)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.leagues ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }

    override suspend fun getAllLeaguesByCountryAndSport(
        countryName: String,
        sportType: String
    ): Resource<List<League>> {
        return try {
            val response =
                api.getLeaguesByCountryAndSport(countryName = countryName, sportName = sportType)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.SUCCESS(it.leagues ?: arrayListOf())
                } ?: Resource.ERROR("An Unknown error occured")
            } else Resource.ERROR("An Unknown error occured")
        } catch (e: Exception) {
            Resource.ERROR(e.message ?: "")
        }
    }


}