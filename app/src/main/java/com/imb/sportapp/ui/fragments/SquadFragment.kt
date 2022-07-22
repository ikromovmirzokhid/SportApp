package com.imb.sportapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import com.imb.sportapp.R
import com.imb.sportapp.adapters.PlayerListAdapter
import com.imb.sportapp.databinding.FragmentSquadBinding
import com.imb.sportapp.models.Player
import com.imb.sportapp.utils.getDataList
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.putData
import com.imb.sportapp.utils.visible

class SquadFragment : BaseFragment(R.layout.fragment_squad) {

    private var players = ArrayList<Player>()
    private lateinit var binding: FragmentSquadBinding

    companion object {
        fun newInstance(d: List<Player>): SquadFragment {
            val args = Bundle()
            args.putData("items", d)
            val fragment = SquadFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSquadBinding.bind(view)

        if (!requireArguments().isEmpty) {
            players.clear()
            players.addAll(
                arguments?.getDataList<List<Player>>(
                    "items",
                    object : TypeToken<List<Player>>() {}.type
                ) ?: arrayListOf()
            )
        }

        initViews()
    }

    private fun initViews() {
        binding.apply {
            val adapter = PlayerListAdapter(players)
            playersRv.layoutManager = LinearLayoutManager(requireContext())
            playersRv.adapter = adapter

            if (players.isEmpty())
                dataNotFoundLayout.visible()
            else
                dataNotFoundLayout.gone()
        }
    }
}