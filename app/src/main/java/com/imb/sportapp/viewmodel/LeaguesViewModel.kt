package com.imb.sportapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imb.sportapp.models.League
import com.imb.sportapp.models.Resource
import com.imb.sportapp.repos.LeagueRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LeaguesViewModel @Inject constructor(private val repository: LeagueRepository) : ViewModel() {

    private var countriesLiveData: MutableLiveData<Resource<List<String>>> = MutableLiveData()

    fun getAllCountries(): LiveData<Resource<List<String>>> {
        countriesLiveData = MutableLiveData()

        viewModelScope.launch {
            countriesLiveData.postValue(repository.getAllCountries())
        }

        return countriesLiveData
    }


    private var sportTypesLiveData: MutableLiveData<Resource<List<String>>> = MutableLiveData()

    fun getAllSportTypes(): LiveData<Resource<List<String>>> {
        sportTypesLiveData = MutableLiveData()

        viewModelScope.launch {
            sportTypesLiveData.postValue(repository.getAllSportTypes())
        }

        return sportTypesLiveData
    }

    private var leaguesLiveData: MutableLiveData<Resource<List<League>>> = MutableLiveData()

    fun getLeaguesByCountry(
        countryName: String
    ): LiveData<Resource<List<League>>> {
        leaguesLiveData = MutableLiveData()

        viewModelScope.launch {
            leaguesLiveData.postValue(
                repository.getAllLeaguesByCountry(
                    countryName
                )
            )
        }

        return leaguesLiveData
    }

    fun getLeaguesByCountryAndSport(
        countryName: String,
        sportType: String
    ): LiveData<Resource<List<League>>> {
        leaguesLiveData = MutableLiveData()

        viewModelScope.launch {
            leaguesLiveData.postValue(
                repository.getAllLeaguesByCountryAndSport(
                    countryName,
                    sportType
                )
            )
        }

        return leaguesLiveData
    }


}