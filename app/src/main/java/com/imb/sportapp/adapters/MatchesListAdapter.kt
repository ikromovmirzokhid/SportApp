package com.imb.sportapp.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.imb.sportapp.R
import com.imb.sportapp.databinding.MatchesListItemBinding
import com.imb.sportapp.models.Match
import com.imb.sportapp.utils.GlideApp
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MatchesListAdapter(private val matches: List<Match>) :
    RecyclerView.Adapter<MatchesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        MatchesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    override fun getItemCount(): Int = matches.size

    class ViewHolder(private val binding: MatchesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.apply {
                firstTeam.text = match.homeTeam
                secondTeam.text = match.awayTeam
                match.homeScore?.let {
                    firstTeamScore.visible()
                    firstTeamScore.text = it
                }

                match.awayScore?.let {
                    secondTeamTeamScore.visible()
                    secondTeamTeamScore.text = it
                }

                matchDate.text = match.date

                if (match.homeTeam == null && match.awayTeam == null) {
                    matchName.visible()
                    matchName.text = match.matchName
                }

                if (match.thumb != "") {
                    matchImg.visible()
                    matchImageProgress.visible()
                    GlideApp.with(binding.root.context).load(match.thumb)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                matchImageProgress.gone()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                matchImageProgress.gone()
                                return false
                            }

                        })
                        .apply(
                            RequestOptions()
                                .error(R.drawable.sport_image)
                        )
                        .into(matchImg)
                }
            }
        }

    }
}