package com.imb.sportapp.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.imb.sportapp.R
import com.imb.sportapp.databinding.PlayersListItemBinding
import com.imb.sportapp.models.Player
import com.imb.sportapp.utils.GlideApp
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible

class PlayerListAdapter(private val players: List<Player>) :
    RecyclerView.Adapter<PlayerListAdapter.ViewHolder>() {

//    private lateinit var clickListener: (Player) -> Unit
//
//    fun setOnItemClickListener(f: (Player) -> Unit) {
//        clickListener = f
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        PlayersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    inner class ViewHolder(private val binding: PlayersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
//            itemView.setOnClickListener {
//                clickListener(player)
//            }
            binding.apply {
                playerName.text = player.name
                playerNationality.text = player.nationality
                playerNumber.text = player.number
                val context = binding.root.context
                if (player.photo != "" && player.photo != null) {
                    setImage(context, playerImage, teamImageProgress, player.photo)
                } else {
                    when (player.gender) {
                        "Male" -> {
                            setImage(
                                context,
                                playerImage,
                                teamImageProgress,
                                drawable = R.drawable.man
                            )
                        }
                        "Female" -> {
                            setImage(
                                context,
                                playerImage,
                                teamImageProgress,
                                drawable = R.drawable.woman
                            )
                        }
                    }
                }
            }
        }

        fun setImage(
            context: Context,
            imageView: ImageView,
            progress: ProgressBar,
            url: String? = null,
            drawable: Int? = null
        ) {
            progress.visible()
            GlideApp.with(context).load(url ?: drawable)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.gone()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.gone()
                        return false
                    }

                })
                .into(imageView)
        }

    }
}