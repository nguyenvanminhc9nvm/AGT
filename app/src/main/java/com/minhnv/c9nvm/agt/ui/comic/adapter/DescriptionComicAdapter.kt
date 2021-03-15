package com.minhnv.c9nvm.agt.ui.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_description_comic.view.*

class DescriptionComicAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSource: MutableList<DescriptionComic> = mutableListOf()

    private inner class DescriptionComicViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(descriptionComic: DescriptionComic) {
            itemView.imgDescriptionComic.loadImage(
                AGTConstant.PATH_DESCRIPTION_COMIC,
                descriptionComic.imageUrl
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_description_comic, parent, false)
        return DescriptionComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DescriptionComicViewHolder) {
            holder.bind(dataSource[position])
        }
    }

    fun set(list: List<DescriptionComic>) {
        dataSource.clear()
        dataSource.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}