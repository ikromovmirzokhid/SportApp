package com.imb.sportapp.repos

import com.imb.sportapp.models.League
import com.imb.sportapp.models.Resource

interface LeagueRepository {

    suspend fun getAllCountries() : Resource<List<String>>

    suspend fun getAllSportTypes() : Resource<List<String>>

    suspend fun getAllLeaguesByCountry(countryName : String) : Resource<List<League>>

    suspend fun getAllLeaguesByCountryAndSport(countryName : String, sportType : String) : Resource<List<League>>
}