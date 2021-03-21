package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_comic.view.*
import kotlinx.android.synthetic.main.item_expanded_size.view.*

class ComicAdapter(
    private val context: Context,
    private val onClick: (Comic) -> Unit
) : PagingDataAdapter<Comic, RecyclerView.ViewHolder>(DataDifferentiators) {

    private val adsExpandedSize = 1
    private val adsExpandedSize1 = 15
    private val adsExpandedSize2 = 25
    private val adsExpandedSize3 = 40

    private val itemComic = 0
    private val itemAdFullSize = adsExpandedSize
    private val itemAdFullSize1 = adsExpandedSize1
    private val itemAdFullSize2 = adsExpandedSize2
    private val itemAdFullSize3 = adsExpandedSize3

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comic: Comic?) {
            comic?.let {
                itemView.setOnClickListener {
                    onClick.invoke(comic)
                }
                itemView.imgComic.loadImage(AGTConstant.PATH_COMIC, comic.comicImage)
                itemView.tvNameComic.text = comic.nameComic
                itemView.tvCreateAt.text =
                    String.format("${context.getText(R.string.time_post)} ${comic.createAt}")
            }
        }
    }

    object DataDifferentiators : DiffUtil.ItemCallback<Comic>() {

        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }
    }

    private var uniFieldAd: UnifiedNativeAd? = null

    fun setAd(uni: UnifiedNativeAd) {
        uniFieldAd = uni
    }

    inner class AdsExpandedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            uniFieldAd?.let {
                itemView.templateExpanded.setNativeAd(it)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicViewHolder -> {
                holder.bind(getItem(position))
            }
            is AdsExpandedViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            itemComic -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
                ComicViewHolder(view)
            }
            itemAdFullSize, itemAdFullSize1, itemAdFullSize2, itemAdFullSize3 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_expanded_size, parent, false)
                AdsExpandedViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
                ComicViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemComic -> 0
            itemAdFullSize -> adsExpandedSize
            itemAdFullSize1 -> adsExpandedSize1
            itemAdFullSize2 -> adsExpandedSize2
            itemAdFullSize3 -> adsExpandedSize3
            else -> -1
        }
    }
}