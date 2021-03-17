package com.minhnv.c9nvm.agt.utils.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import kotlinx.android.synthetic.main.item_loading.view.*

class FooterAdapter : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {
    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(loadState: LoadState) {
            if (loadState == LoadState.Loading) {
                itemView.progressBar.visibility = View.VISIBLE
            } else {
                itemView.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return FooterViewHolder(view)
    }
}