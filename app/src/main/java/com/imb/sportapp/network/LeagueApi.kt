package com.imb.sportapp.network

import com.imb.sportapp.BuildConfig
import com.imb.sportapp.models.CountriesResponse
import com.imb.sportapp.models.LeaguesResponse
import com.imb.sportapp.models.SportsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface LeagueApi {

    @GET("{API_KEY}/all_countries.php")
    suspend fun getAllCountries(@Path("API_KEY") apiKey: String = BuildConfig.API_KEY): Response<CountriesResponse>

    @GET("{API_KEY}/all_sports.php")
    suspend fun getAllSports(@Path("API_KEY") apiKey: String = BuildConfig.API_KEY): Response<SportsResponse>

    @GET("{API_KEY}/search_all_leagues.php")
    suspend fun getLeaguesByCountry(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("c") countryName: String
    ): Response<LeaguesResponse>

    @GET("{API_KEY}/search_all_leagues.php")
    suspend fun getLeaguesByCountryAndSport(
        @Path("API_KEY") apiKey: String = BuildConfig.API_KEY,
        @Query("c") countryName: String,
        @Query("s") sportName: String,
    ): Response<LeaguesResponse>


}