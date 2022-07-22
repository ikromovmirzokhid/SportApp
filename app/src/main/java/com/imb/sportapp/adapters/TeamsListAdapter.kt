package com.imb.sportapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.imb.sportapp.R
import com.imb.sportapp.databinding.TeamListItemBinding
import com.imb.sportapp.models.Team
import com.imb.sportapp.utils.GlideApp
import com.imb.sportapp.utils.HawkUtils
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible

class TeamsListAdapter : RecyclerView.Adapter<TeamsListAdapter.ViewHolder>() {

    private var teams = arrayListOf<Team>()
    private lateinit var clickListener: (Team) -> Unit

    fun setOnItemClickListener(f: (Team) -> Unit) {
        clickListener = f
    }

    fun updateData(items: List<Team>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = teams.size

            override fun getNewListSize(): Int = items.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return if (teams.isNotEmpty())
                    teams[oldPos].idTeam == items[newPos].idTeam
                else false
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return if (teams.isNotEmpty())
                    teams[oldPos].hashCode() == items[newPos].hashCode()
                else false
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        teams.clear()
        teams.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    override fun getItemCount(): Int = teams.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = TeamListItemBinding.bind(itemView)
        fun bind(team: Team) {
            itemView.setOnClickListener {
                HawkUtils.favoriteTeamId = team.idTeam
                HawkUtils.favoriteTeam = team.teamName
                clickListener(team)
            }

            binding.apply {
                teamName.text = team.teamName
                teamImage.apply {
                    binding.teamImageProgress.visible()
                    GlideApp.with(context).load(team.badge ?: team.logo)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.teamImageProgress.gone()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding.teamImageProgress.gone()
                                return false
                            }

                        })
                        .apply(
                            RequestOptions()
                                .error(R.drawable.star)
                        )
                        .into(this)
                }
            }
        }

    }
}