package com.imb.sportapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import com.imb.sportapp.R
import com.imb.sportapp.adapters.MatchesListAdapter
import com.imb.sportapp.databinding.FragmentMatchesBinding
import com.imb.sportapp.models.Match
import com.imb.sportapp.utils.getDataList
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.putData
import com.imb.sportapp.utils.visible
import kotlin.collections.ArrayList

class MatchesFragment : BaseFragment(R.layout.fragment_matches) {

    private var allMatches = ArrayList<Match>()
    private lateinit var binding: FragmentMatchesBinding

    companion object {
        fun newInstance(d: List<Match>): MatchesFragment {
            val args = Bundle()
            args.putData("items", d)
            val fragment = MatchesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMatchesBinding.bind(view)

        if (!requireArguments().isEmpty) {
            allMatches.clear()
            allMatches.addAll(
                arguments?.getDataList<List<Match>>(
                    "items",
                    object : TypeToken<List<Match>>() {}.type
                ) ?: arrayListOf()
            )
        }
        initViews()
    }

    private fun initViews() {
        binding.apply {
            val adapter = MatchesListAdapter(allMatches)
            matchesRv.layoutManager = LinearLayoutManager(requireContext())
            matchesRv.adapter = adapter

            if(allMatches.isEmpty())
                dataNotFoundLayout.visible()
            else
                dataNotFoundLayout.gone()
        }
    }
}