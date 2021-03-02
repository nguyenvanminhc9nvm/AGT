package com.minhnv.c9nvm.agt.ui.humor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.databinding.ItemHumorBinding
import com.minhnv.c9nvm.agt.ui.base.BaseRecyclerView

class HumorAdapter : BaseRecyclerView<Humor>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemHumorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HumorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class HumorViewHolder(private val binding: ItemHumorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(humor: Humor) {

        }
    }



}