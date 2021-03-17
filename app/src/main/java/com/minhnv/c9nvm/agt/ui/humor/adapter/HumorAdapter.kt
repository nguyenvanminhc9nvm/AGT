package com.minhnv.c9nvm.agt.ui.humor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_humor.view.*

class HumorAdapter(private val context: Context) :
    PagingDataAdapter<Humor, HumorAdapter.HumorViewHolder>(DataDifferentiators) {

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

    override fun onBindViewHolder(holder: HumorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HumorAdapter.HumorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_humor, parent, false)
        return HumorViewHolder(view)
    }
}