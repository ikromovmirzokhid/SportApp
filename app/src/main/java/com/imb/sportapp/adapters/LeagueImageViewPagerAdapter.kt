package com.imb.sportapp.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.imb.sportapp.R
import com.imb.sportapp.databinding.LeagueImageViewPagerBinding
import com.imb.sportapp.models.League
import com.imb.sportapp.utils.GlideApp
import com.imb.sportapp.utils.gone
import com.imb.sportapp.utils.visible

class LeagueImageViewPagerAdapter(
    private val context: Context,
    private val images: List<String>
) :
    RecyclerView.Adapter<LeagueImageViewPagerAdapter.ViewHolder>() {

    private lateinit var clickListener: () -> Unit

    fun setOnItemClickListener(f: () -> Unit) {
        clickListener = f
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            LeagueImageViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(private var itemHolderBinding: LeagueImageViewPagerBinding) :
        RecyclerView.ViewHolder(itemHolderBinding.root) {
        fun bind(imageUrl: String) {
            itemHolderBinding.leagueImage.apply {
                setOnClickListener {
                    clickListener.invoke()
                }
//                Glide.with(context).load(imageUrl).into(this)
                itemHolderBinding.leagueImageProgress.visible()
                GlideApp.with(context).load(imageUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemHolderBinding.leagueImageProgress.gone()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            itemHolderBinding.leagueImageProgress.gone()
                            return false
                        }

                    })
                    .override(360, 200).apply(
                        RequestOptions()
                            .error(R.drawable.sport_image)
                    )
                    .into(this)
            }
        }
    }

}