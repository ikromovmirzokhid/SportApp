package com.imb.sportapp.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.gson.reflect.TypeToken
import com.imb.sportapp.R
import com.imb.sportapp.models.Match
import com.imb.sportapp.models.Player
import com.imb.sportapp.models.Team
import com.imb.sportapp.utils.getData
import com.imb.sportapp.utils.getDataList
import com.imb.sportapp.utils.putData

class TeamDetailsFragment : BaseFragment(R.layout.fragment_team_details) {

    private var team: Team? = null

    companion object {
        fun newInstance(d: Team): TeamDetailsFragment {
            val args = Bundle()
            args.putData("team", d)
            val fragment = TeamDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!requireArguments().isEmpty) {
            team = arguments?.getData<Team>("team")

        }

    }
}