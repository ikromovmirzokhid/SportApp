package com.imb.sportapp.ui.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOff
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOn
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.showError
import com.imb.sportapp.R
import com.imb.sportapp.databinding.FragmentTeamHomeBinding
import com.imb.sportapp.di.components.MainComponent
import com.imb.sportapp.models.Match
import com.imb.sportapp.models.Player
import com.imb.sportapp.models.Resource
import com.imb.sportapp.models.Team
import com.imb.sportapp.utils.HawkUtils
import com.imb.sportapp.viewmodel.TeamsViewModel

class TeamHomeFragment : BaseFragment(R.layout.fragment_team_home) {

    lateinit var binding: FragmentTeamHomeBinding

    private val pages = arrayListOf<Fragment>()

    private val component: MainComponent by lazy {
        MainComponent.create()
    }
    private lateinit var teamsViewModel: TeamsViewModel
    private var teamId: String? = null

    private var dataLoaded = false
    private var isTeamDataLoaded = false
    private var isTeamMatchesLoaded = false
    private var isTeamSquadLoaded = false
    private var team: Team? = null

    private val players = arrayListOf<Player>()
    private val lastMatches = arrayListOf<Match>()
    private val nextMatches = arrayListOf<Match>()
    private val allMatches = arrayListOf<Match>()

    private var isLastMatchesLoaded = false
    private var isNextMatchesLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTeamHomeBinding.bind(view)

        if (!isDetached) {
            teamsViewModel = component.viewModelProvider().create(TeamsViewModel::class.java)
        }

        teamId = HawkUtils.favoriteTeamId

        if (!dataLoaded)
            loadTeamData()

    }

    private var activePage = 0

    private fun select(i: Int) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.baseAppColor)
        clearColor()
        activePage = i
        binding.apply {

            when (i) {
                0 -> {
                    cardImg1.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN)
                    textTabPage1.setTextColor(activeColor)
                }
                1 -> {
                    cardImg2.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN)
                    textTabPage2.setTextColor(activeColor)
                }
//                2 -> {
//                    cardImg3.setColorFilter(activeColor, PorterDuff.Mode.SRC_IN)
//                    textTabPage3.setTextColor(activeColor)
//                }
            }
        }

        if (i < pages.size) {
            chooseFragment(i)
        }
    }

    private fun chooseFragment(i: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container, pages[i])
            .commit()
    }

    private fun clearColor() {

        binding.apply {
            val disableColor = ContextCompat.getColor(requireContext(), R.color.colorDisable)

            cardImg1.setColorFilter(disableColor, PorterDuff.Mode.SRC_IN)
            textTabPage1.setTextColor(disableColor)

            cardImg2.setColorFilter(disableColor, PorterDuff.Mode.SRC_IN)
            textTabPage2.setTextColor(disableColor)

//            cardImg3.setColorFilter(disableColor, PorterDuff.Mode.SRC_IN)
//            textTabPage3.setTextColor(disableColor)
        }
    }

    private fun loadTeamData() {
        teamId?.let {
            progressOn()
            teamsViewModel.getTeamById(it).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.SUCCESS -> {
                        isTeamDataLoaded = true
                        team = response.data
                        initViews()
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(response.errMsg ?: "")
                        isTeamDataLoaded = false
                        dataLoaded = false
                    }
                }
            }

            teamsViewModel.getTeamNextEvents(it).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.SUCCESS -> {
                        isNextMatchesLoaded = true
                        nextMatches.clear()
                        nextMatches.addAll(response.data ?: arrayListOf())
                        if (isLastMatchesLoaded && isNextMatchesLoaded) {
                            isTeamMatchesLoaded = true
                            allMatches.clear()
                            allMatches.addAll(lastMatches)
                            allMatches.addAll(nextMatches)
                            initViews()
                        }
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(response.errMsg ?: "")
                        dataLoaded = false
                        isTeamMatchesLoaded = false
                    }
                }
            }

            teamsViewModel.getTeamLastEvents(it).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.SUCCESS -> {
                        isLastMatchesLoaded = true
                        lastMatches.clear()
                        lastMatches.addAll(response.data ?: arrayListOf())
                        if (isLastMatchesLoaded && isNextMatchesLoaded) {
                            isTeamMatchesLoaded = true
                            allMatches.clear()
                            allMatches.addAll(lastMatches)
                            allMatches.addAll(nextMatches)
                            initViews()
                        }
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(response.errMsg ?: "")
                        dataLoaded = false
                        isTeamMatchesLoaded = false
                    }
                }
            }

            teamsViewModel.getPlayersByTeamId(it).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.SUCCESS -> {
                        isTeamSquadLoaded = true
                        players.clear()
                        players.addAll(response.data ?: arrayListOf())
                        initViews()
                    }
                    is Resource.ERROR -> {
                        progressOff()
                        showError(response.errMsg ?: "")
                        isTeamSquadLoaded = false
                        dataLoaded = false
                    }
                }
            }
        }
    }

    private fun initViews() {
        if (isTeamDataLoaded && isTeamSquadLoaded && isTeamMatchesLoaded) {
            binding.apply {
                toolbarMain.inflateMenu(R.menu.main_menu)
                toolbarMain.setOnMenuItemClickListener {
                    onOptionsItemSelected(it)
                }
            }
            dataLoaded = true
            pages.add(MatchesFragment.newInstance(allMatches))
            pages.add(SquadFragment.newInstance(players))
            team?.let {
                pages.add(TeamDetailsFragment.newInstance(it))
            }
            progressOff()
            team?.let {
                binding.toolbarMain.title = it.teamName
                Glide.with(requireContext()).load(it.badge ?: it.logo).error(R.drawable.star)
                    .into(binding.logoInToolbar)
                initializeWindow()
            }
        }
    }

    private fun initializeWindow() {
        select(activePage)

        binding.run {
            pageView1.setOnClickListener {
                select(0)
            }
            pageView2.setOnClickListener {
                select(1)
            }
//            pageView3.setOnClickListener {
//                select(2)
//            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_favorite_team -> {
                HawkUtils.favoriteTeam = ""
                HawkUtils.favoriteTeamId = ""
                findNavController().navigate(R.id.action_teamHomeFragment_to_leaguesFragment)
            }
        }
        return true
    }
}