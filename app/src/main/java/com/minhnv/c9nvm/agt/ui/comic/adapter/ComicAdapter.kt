package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicAdapter(
    private val context: Context,
    private val onClick: (Comic) -> Unit
) : PagingDataAdapter<Comic, ComicAdapter.ComicViewHolder>(DataDifferentiators) {

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

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicViewHolder(view)
    }
}