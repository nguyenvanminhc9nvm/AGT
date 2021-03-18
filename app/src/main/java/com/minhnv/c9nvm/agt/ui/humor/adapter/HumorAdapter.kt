package com.minhnv.c9nvm.agt.ui.humor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.nativead.NativeAd
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_ad_unifield.view.*
import kotlinx.android.synthetic.main.item_humor.view.*

class HumorAdapter(
    private val context: Context,
    private val nativeAd: NativeAd?
) :
    PagingDataAdapter<Humor, RecyclerView.ViewHolder>(DataDifferentiators) {

    companion object {
        const val ITEM_HUMOR = 0
        const val ITEM_ADMOB = 13
        const val ITEM_ADMOB_1 = 33
        const val ITEM_ADMOB_2 = 53
    }

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
            ITEM_HUMOR -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_humor, parent, false)
                HumorViewHolder(view)
            }
            ITEM_ADMOB -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ad_unifield, parent, false)
                AdsViewHolder(view)
            }
            ITEM_ADMOB_1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ad_unifield, parent, false)
                AdsViewHolder(view)
            }
            ITEM_ADMOB_2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_ad_unifield, parent, false)
                AdsViewHolder(view)
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
            ITEM_HUMOR -> 0
            ITEM_ADMOB -> 15
            ITEM_ADMOB_1 -> 35
            ITEM_ADMOB_2 -> 55
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HumorViewHolder -> {
                holder.bind(getItem(position))
            }
            is AdsViewHolder -> {
                holder.bind(nativeAd)
            }
        }
    }

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nativeAd: NativeAd?) {
            nativeAd?.let {
                itemView.ad_media.setMediaContent(nativeAd.mediaContent)
                itemView.ad_headline.text = nativeAd.headline
                itemView.ad_body.text = nativeAd.body
                itemView.ad_call_to_action.text = nativeAd.callToAction
                itemView.ad_app_icon.setImageDrawable(nativeAd.icon.drawable)
                itemView.ad_price.text = nativeAd.price
                itemView.ad_stars.rating = nativeAd.starRating.toFloat()
                itemView.ad_store.text = nativeAd.store
                itemView.ad_advertiser.text = nativeAd.advertiser
            }
        }
    }

}