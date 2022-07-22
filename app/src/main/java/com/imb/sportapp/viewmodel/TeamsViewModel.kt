package com.imb.sportapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imb.sportapp.models.Match
import com.imb.sportapp.models.Player
import com.imb.sportapp.models.Resource
import com.imb.sportapp.models.Team
import com.imb.sportapp.repos.TeamRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamsViewModel @Inject constructor(private val repository: TeamRepository) : ViewModel() {

    private var teamsLiveData: MutableLiveData<Resource<List<Team>>> = MutableLiveData()

    fun getTeamsByLeagueId(id: String): LiveData<Resource<List<Team>>> {
        teamsLiveData = MutableLiveData()

        viewModelScope.launch {
            teamsLiveData.postValue(repository.getTeamsByLeagueId(id))
        }

        return teamsLiveData
    }

    fun getTeamsByLeagueName(name: String): LiveData<Resource<List<Team>>> {
        teamsLiveData = MutableLiveData()

        viewModelScope.launch {
            teamsLiveData.postValue(repository.getTeamsByLeagueName(name))
        }

        return teamsLiveData
    }

    private var teamLiveData: MutableLiveData<Resource<Team>> = MutableLiveData()

    fun getTeamById(id: String): LiveData<Resource<Team>> {
        teamLiveData = MutableLiveData()

        viewModelScope.launch {
            teamLiveData.postValue(repository.getTeamById(id))
        }

        return teamLiveData
    }

    private var nextMatchesLiveData: MutableLiveData<Resource<List<Match>>> = MutableLiveData()

    fun getTeamNextEvents(id: String): LiveData<Resource<List<Match>>> {
        nextMatchesLiveData = MutableLiveData()

        viewModelScope.launch {
            nextMatchesLiveData.postValue(repository.getTeamNextEvents(id))
        }

        return nextMatchesLiveData
    }

    private var lastMatchesLiveData: MutableLiveData<Resource<List<Match>>> = MutableLiveData()

    fun getTeamLastEvents(id: String): LiveData<Resource<List<Match>>> {
        lastMatchesLiveData = MutableLiveData()

        viewModelScope.launch {
            lastMatchesLiveData.postValue(repository.getTeamLastEvents(id))
        }

        return lastMatchesLiveData
    }

    private var playersLiveData: MutableLiveData<Resource<List<Player>>> = MutableLiveData()

    fun getPlayersByTeamId(id: String): LiveData<Resource<List<Player>>> {
        playersLiveData = MutableLiveData()

        viewModelScope.launch {
            playersLiveData.postValue(repository.getPlayersByTeamId(id))
        }

        return playersLiveData
    }

}