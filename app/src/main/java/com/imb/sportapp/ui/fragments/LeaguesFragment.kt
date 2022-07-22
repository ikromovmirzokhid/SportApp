package com.imb.sportapp.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOff
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOn
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.showError
import com.imb.sportapp.R
import com.imb.sportapp.adapters.LeagueListAdapter
import com.imb.sportapp.databinding.FragmentLeaguesBinding
import com.imb.sportapp.di.components.MainComponent
import com.imb.sportapp.models.League
import com.imb.sportapp.models.Resource
import com.imb.sportapp.utils.HawkUtils
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible
import com.imb.sportapp.viewmodel.LeaguesViewModel
import java.util.*


class LeaguesFragment : BaseFragment(R.layout.fragment_leagues) {

    lateinit var binding: FragmentLeaguesBinding
    private val component: MainComponent by lazy {
        MainComponent.create()
    }
    private lateinit var leagueViewModel: LeaguesViewModel
    private var isCountriesLoaded = false
    private var isSportsLoaded = false

    private var countriesList = arrayListOf<String>()
    private var sportsList = arrayListOf<String>()
    private var leaguesData = arrayListOf<League>()

    private var selectedCountry = "United States"
    private var selectedSport = "All"

    private lateinit var leaguesListAdapter: LeagueListAdapter
    private var dataLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLeaguesBinding.bind(view)

        val teamId = HawkUtils.favoriteTeamId
        if (teamId != "") {
            findNavController().navigate(R.id.action_leaguesFragment_to_teamHomeFragment)
        }

        val isTeamKnown = HawkUtils.favoriteTeamId != ""

        if (!isTeamKnown) {

            if (!isDetached) {
                leagueViewModel = component.viewModelProvider().create(LeaguesViewModel::class.java)
            }

            initViews()
            if (!dataLoaded)
                loadSpinnerData()
            else
                restoreLastData()
            initListeners()
        }
    }

    private fun restoreLastData() {
        initSpinnerViewsWithData()
    }

    private fun initListeners() {

        binding.sportsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                p0?.getChildAt(0)?.let {
                    (it as TextView).setTextColor(Color.WHITE)
                }
                selectedSport = sportsList[p2]
                firebaseAnalytics.logEvent("Chosen_Sport_Type", bundleOf("sport" to selectedSport))
                loadLeaguesData()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.countriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    p0?.getChildAt(0)?.let {
                        (it as TextView).setTextColor(Color.WHITE)
                    }
                    selectedCountry = countriesList[p2]
                    firebaseAnalytics.logEvent("Chosen_Country", bundleOf("country" to selectedCountry))
                    loadLeaguesData()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

        leaguesListAdapter.setOnItemClickListener {
            firebaseAnalytics.logEvent("Chosen_League", bundleOf("league" to it.nameLeague))
            findNavController().navigate(
                R.id.action_leaguesFragment_to_teamsFragment,
                bundleOf("leagueId" to it.idLeague)
            )
        }

        leaguesListAdapter.setOnLinkClickListener {
            openBrowser(it)
        }
    }

    private fun loadLeaguesData() {
        dataLoaded = true
        progressOn()
        when (selectedSport) {
            "All" -> leagueViewModel.getLeaguesByCountry(selectedCountry)
                .observe(viewLifecycleOwner) {
                    setLeaguesData(it)
                }
            else -> leagueViewModel.getLeaguesByCountryAndSport(selectedCountry, selectedSport)
                .observe(viewLifecycleOwner) {
                    setLeaguesData(it)
                }
        }

    }

    private fun setLeaguesData(response: Resource<List<League>>?) {
        progressOff()
        when (response) {
            is Resource.SUCCESS -> {
                response.data?.let { data ->
                    leaguesData.clear()
                    leaguesData.addAll(data)
                    leaguesListAdapter.updateData(data)

                    if (leaguesData.isEmpty())
                        binding.secondaryView.dataNotFoundLayout.visible()
                    else
                        binding.secondaryView.dataNotFoundLayout.gone()
                }
            }
            is Resource.ERROR -> {
                progressOff()
                showError(response.errMsg ?: "")
                dataLoaded = false
            }
            else -> {
                showError("Unknown Error")
            }
        }
    }

    private fun loadSpinnerData() {
        progressOn()
        leagueViewModel.getAllCountries().observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.SUCCESS -> {
                        it.data?.let { data ->
                            Collections.sort(data)
                            countriesList.clear()
                            countriesList.addAll(data)
                        }
                        isCountriesLoaded = true
                        if (isCountriesLoaded && isSportsLoaded) {
                            progressOff()
                            initSpinnerViewsWithData()
                        }
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(it.errMsg ?: "")
                        dataLoaded = false
                    }
                }
            }
        }

        leagueViewModel.getAllSportTypes().observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is Resource.SUCCESS -> {
                        it.data?.let { data ->
                            Collections.sort(data)
                            sportsList.clear()
                            sportsList.add(0, "All")
                            sportsList.addAll(data)
                        }
                        isSportsLoaded = true
                        if (isCountriesLoaded && isSportsLoaded) {
                            progressOff()
                            initSpinnerViewsWithData()
                        }
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(it.errMsg ?: "")
                        dataLoaded = false
                    }
                }
            }
        }
    }

    private fun initSpinnerViewsWithData() {
        initSportListSpinner()
        initCountryListSpinner()
    }

    private fun initSportListSpinner() {
        binding.sportsSpinner.apply {
            visible()
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    sportsList
                )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
        }
    }

    private fun initCountryListSpinner() {
        binding.countriesSpinner.apply {
            visible()
            val dataAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    countriesList
                )
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = dataAdapter
        }

        val defaultCountryIndex = countriesList.indexOf("United States")
        binding.countriesSpinner.setSelection(defaultCountryIndex)
    }

    private fun initViews() {
        leaguesListAdapter = LeagueListAdapter()
        leaguesListAdapter.updateData(leaguesData)
        binding.leaguesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = leaguesListAdapter
        }

        initAdview()
    }

    private fun initAdview() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun openBrowser(link: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("https://$link")
        requireActivity().startActivity(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        dataLoaded = false
    }
}