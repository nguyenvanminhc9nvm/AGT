package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_detail_comic.view.*

class ComicDetailAdapter(
    private val onClick: (DetailComic) -> Unit
) :
    PagingDataAdapter<DetailComic, ComicDetailAdapter.ComicDetailViewHolder>(DataDifferentiators) {

    inner class ComicDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detailComic: DetailComic?) {
            detailComic?.let {
                itemView.setOnClickListener {
                    onClick.invoke(detailComic)
                }
                itemView.imgDetailComic.loadImage(
                    AGTConstant.PATH_DETAIL_COMIC,
                    detailComic.imageUrl
                )
                itemView.tvTitleComic.text = detailComic.nameComic
                itemView.tvDescriptionComic.text = detailComic.description
            }
        }
    }

    object DataDifferentiators : DiffUtil.ItemCallback<DetailComic>() {

        override fun areItemsTheSame(oldItem: DetailComic, newItem: DetailComic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailComic, newItem: DetailComic): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ComicDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicDetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_comic, parent, false)
        return ComicDetailViewHolder(view)
    }
}