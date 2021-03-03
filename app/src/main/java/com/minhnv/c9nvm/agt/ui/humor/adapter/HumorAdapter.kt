package com.minhnv.c9nvm.agt.ui.humor.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.utils.options.loadImage
import com.minhnv.c9nvm.agt.utils.recycler_view.RecyclerAdapter
import kotlinx.android.synthetic.main.item_humor.view.*

class HumorAdapter(private val context: Context) : RecyclerAdapter<Humor>(context) {

    private val humors = mutableListOf<Humor>()

    inner class HumorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(humor: Humor) {
            itemView.circleImageView.loadImage(humor.imageUrl)
            itemView.tvDescription.text = humor.content
            itemView.tvTime.text = String.format("${context.getText(R.string.time_post)} ${humor.create_at}")
            itemView.tvTitle.text = humor.title
        }
    }

    override var layoutResource: Int = R.layout.item_humor


    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
       return HumorViewHolder(view)
    }

    override fun bindHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HumorViewHolder) {
            holder.bind(getItem(position))
        }
    }


}