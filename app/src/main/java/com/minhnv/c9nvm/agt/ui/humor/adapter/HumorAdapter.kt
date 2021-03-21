package com.minhnv.c9nvm.agt.ui.humor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_ad_unifield.view.*
import kotlinx.android.synthetic.main.item_expanded_size.view.*
import kotlinx.android.synthetic.main.item_humor.view.*


class HumorAdapter(
    private val context: Context
) : PagingDataAdapter<Humor, RecyclerView.ViewHolder>(DataDifferentiators) {

    private val adsCollapseSize = 1
    private val adsCollapseSize1 = 20
    private val adsCollapseSize2 = 60
    private val adsCollapseSize3 = 80
    private val adsExpandedSize = 10
    private val adsExpandedSize1 = 40
    private val adsExpandedSize2 = 90
    private val adsExpandedSize3 = 120

    private val itemHumor = 0
    private val itemAd = adsCollapseSize
    private val itemAd1 = adsCollapseSize1
    private val itemAd2 = adsCollapseSize2
    private val itemAd3 = adsCollapseSize3
    private val itemAdFullSize = adsExpandedSize
    private val itemAdFullSize1 = adsExpandedSize1
    private val itemAdFullSize2 = adsExpandedSize2
    private val itemAdFullSize3 = adsExpandedSize3


    inner class HumorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(humor: Humor?) {
            humor?.let {
                itemView.circleImageView.loadImage(AGTConstant.PATH_HUMOR, humor.imageUrl)
                itemView.imgHumor.loadImage(AGTConstant.PATH_HUMOR, humor.imageUrl)
                itemView.tvDescription.text = humor.content
                itemView.tvTime.text =
                    String.format("${context.getText(R.string.time_post)} ${humor.create_at}")
                itemView.tvTitle.text = humor.title
            }
        }
    }

    object DataDifferentiators : DiffUtil.ItemCallback<Humor>() {

        override fun areItemsTheSame(oldItem: Humor, newItem: Humor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Humor, newItem: Humor): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            itemHumor -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_humor, parent, false)
                HumorViewHolder(view)
            }
            itemAd, itemAd2, itemAd3, itemAd1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ad_unifield, parent, false)
                AdsViewHolder(view)
            }
            itemAdFullSize, itemAdFullSize1, itemAdFullSize2, itemAdFullSize3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_expanded_size, parent, false)
                AdsExpandedViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_humor, parent, false)
                HumorViewHolder(view)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemHumor -> 0
            itemAd -> adsCollapseSize
            itemAdFullSize -> adsExpandedSize
            itemAd1 -> adsCollapseSize1
            itemAd2 -> adsCollapseSize2
            itemAd3 -> adsCollapseSize3
            itemAdFullSize1 -> adsExpandedSize1
            itemAdFullSize2 -> adsExpandedSize2
            itemAdFullSize3 -> adsExpandedSize3
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HumorViewHolder -> {
                holder.bind(getItem(position))
            }
            is AdsViewHolder -> {
                holder.bind()
            }
            is AdsExpandedViewHolder -> {
                holder.bind()
            }
        }
    }

    private var uniFieldAd: UnifiedNativeAd? = null

    fun setAd(uni: UnifiedNativeAd) {
        uniFieldAd = uni
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            uniFieldAd?.let {
                itemView.my_template.setNativeAd(it)
            }
        }
    }

    inner class AdsExpandedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            uniFieldAd?.let {
                itemView.templateExpanded.setNativeAd(it)
            }
        }
    }
}