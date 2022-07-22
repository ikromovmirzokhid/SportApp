package com.imb.sportapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOff
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.progressOn
import com.imb.a20220525_mirzokhidjonikromov_nycschools.utils.showError
import com.imb.sportapp.R
import com.imb.sportapp.adapters.TeamsListAdapter
import com.imb.sportapp.databinding.FragmentTeamsBinding
import com.imb.sportapp.di.components.MainComponent
import com.imb.sportapp.models.Resource
import com.imb.sportapp.models.Team
import com.imb.sportapp.utils.HawkUtils
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible
import com.imb.sportapp.viewmodel.TeamsViewModel

class TeamsFragment : BaseFragment(R.layout.fragment_teams) {

    lateinit var binding: FragmentTeamsBinding
    private val component: MainComponent by lazy {
        MainComponent.create()
    }
    private lateinit var teamsViewModel: TeamsViewModel
    private var teams = arrayListOf<Team>()

    private var leagueId: String? = null
    private lateinit var teamsAdapter: TeamsListAdapter

    private var mInterstitialAd : InterstitialAd? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTeamsBinding.bind(view)

        if (!isDetached) {
            teamsViewModel = component.viewModelProvider().create(TeamsViewModel::class.java)
        }
        leagueId = arguments?.getString("leagueId")

        initViews()
        initListeners()
        loadTeamsData()

    }

    private fun initListeners() {
        teamsAdapter.setOnItemClickListener { team ->
            firebaseAnalytics.logEvent("Chosen_Team", bundleOf("team" to team.teamName))
            firebaseAnalytics.logEvent("Chosen_Team_League", bundleOf("sport" to team.league))
            showInterstitialAd()
            findNavController().navigate(
                R.id.action_teamsFragment_to_teamHomeFragment,
                bundleOf("teamId" to team.idTeam)
            )
        }

        binding.arrowBack.setOnClickListener {
            pressBack()
        }
    }

    private fun initViews() {
        teamsAdapter = TeamsListAdapter()
        binding.teamsRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = teamsAdapter
        }
        initAdview()
        initInterstitialAd()
    }

    private fun initAdview() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun loadTeamsData() {
        progressOn()
        leagueId?.let { id ->
            teamsViewModel.getTeamsByLeagueId(id).observe(viewLifecycleOwner) {
                progressOff()
                when (it) {
                    is Resource.SUCCESS -> {
                        it.data?.let { data ->
                            teams.clear()
                            teams.addAll(data)
                            teamsAdapter.updateData(teams)

                            if (teams.isEmpty())
                                binding.secondaryView.dataNotFoundLayout.visible()
                            else
                                binding.secondaryView.dataNotFoundLayout.gone()
                        }
                    }
                    is Resource.ERROR -> {
                        showError(it.errMsg ?: "")
                    }
                }
            }
        }
    }

    private fun initInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, object  : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                mInterstitialAd = null
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                mInterstitialAd = p0
            }
        })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }
}