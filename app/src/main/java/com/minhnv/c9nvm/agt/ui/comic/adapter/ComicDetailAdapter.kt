package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import com.minhnv.c9nvm.agt.utils.recycler_view.RecyclerAdapter
import kotlinx.android.synthetic.main.item_detail_comic.view.*

class ComicDetailAdapter(
    context: Context,
    onClick: (DetailComic) -> Unit
) : RecyclerAdapter<DetailComic>(context, onClick) {
    override var layoutResource: Int = R.layout.item_detail_comic

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return ComicDetailViewHolder(view)
    }

    override fun bindHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ComicDetailViewHolder) {
            holder.bind(getItem(position))
        }
    }

    private inner class ComicDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(detailComic: DetailComic) {
            itemView.imgDetailComic.loadImage(AGTConstant.PATH_DETAIL_COMIC, detailComic.imageUrl)
            itemView.tvTitleComic.text = detailComic.nameComic
            itemView.tvDescriptionComic.text = detailComic.description
        }
    }
}