package com.imb.sportapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imb.sportapp.R
import com.imb.sportapp.databinding.LeaguesListItemBinding
import com.imb.sportapp.models.League
import com.imb.sportapp.utils.visible


class LeagueListAdapter : RecyclerView.Adapter<LeagueListAdapter.ViewHolder>() {

    private var leagues = arrayListOf<League>()
    private lateinit var clickListener: (League) -> Unit
    private lateinit var linkClickListener: (String) -> Unit

    fun setOnItemClickListener(f: (League) -> Unit) {
        clickListener = f
    }

    fun setOnLinkClickListener(f: (String) -> Unit) {
        linkClickListener = f
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueListAdapter.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.leagues_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: LeagueListAdapter.ViewHolder, position: Int) {
        holder.bind(leagues[position])
    }

    fun updateData(items: List<League>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = leagues.size

            override fun getNewListSize(): Int = items.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return if (leagues.isNotEmpty())
                    leagues[oldPos].idLeague == items[newPos].idLeague
                else false
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return if (leagues.isNotEmpty())
                    leagues[oldPos].hashCode() == items[newPos].hashCode()
                else false
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        leagues.clear()
        leagues.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = leagues.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LeaguesListItemBinding.bind(itemView)

        fun bind(league: League) {
            val images = arrayListOf<String>()
            league.art1?.let {
                images.add(it)
            }
            league.art2?.let {
                images.add(it)
            }
            league.art3?.let {
                images.add(it)
            }
            league.art4?.let {
                images.add(it)
            }
            if (images.isEmpty()) {
                league.logo?.let {
                    images.add(it)
                }
                if (images.isEmpty()) {
                    league.badge?.let {
                        images.add(it)
                    }
                }
            }
            binding.leagueItem.setOnClickListener {
                clickListener.invoke(league)
            }
            binding.apply {
                sportType.text = league.typeLeague
                leagueName.text = league.nameLeague
                leagueDesc.text = league.description

                league.instagramPage?.let { link ->
                    if (link != "") {
                        instagram.visible()
                        instagram.setOnClickListener {
                            linkClickListener(link)
                        }
                    }
                }

                league.facebookPage?.let { link ->
                    if(link != ""){
                        facebook.visible()
                        facebook.setOnClickListener {
                            linkClickListener(link)
                        }
                    }
                }

                league.twitterPage?.let { link ->
                    if(link != ""){
                        twitter.visible()
                        twitter.setOnClickListener {
                            linkClickListener(link)
                        }
                    }
                }

                league.website?.let { link ->
                    if(link != ""){
                        website.visible()
                        website.setOnClickListener {
                            linkClickListener(link)
                        }
                    }
                }

                val imagesAdapter = LeagueImageViewPagerAdapter(itemView.context, images)
                imagesAdapter.setOnItemClickListener {
                    clickListener.invoke(league)
                }
                leagueImgViewPager.adapter = imagesAdapter
                binding.indicator.setViewPager(leagueImgViewPager)
            }
        }
    }
}