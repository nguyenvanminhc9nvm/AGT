package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import com.minhnv.c9nvm.agt.utils.recycler_view.RecyclerAdapter
import kotlinx.android.synthetic.main.item_comic.view.*

class ComicAdapter(private val context: Context) : RecyclerAdapter<Comic>(context) {
    override var layoutResource: Int = R.layout.item_comic

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return ComicViewHolder(view)
    }

    override fun bindHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ComicViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comic: Comic) {
            itemView.imgComic.loadImage(AGTConstant.PATH_COMIC, comic.comicImage)
            itemView.tvNameComic.text = comic.nameComic
            itemView.tvCreateAt.text = String.format("${context.getText(R.string.time_post)} ${comic.createAt}")
        }
    }
}