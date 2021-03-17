package com.minhnv.c9nvm.agt.ui.sport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.data.model.Score
import com.minhnv.c9nvm.agt.ui.base.BaseRecyclerView
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.loadImage
import kotlinx.android.synthetic.main.item_sport.view.*

class ScoreAdapter : BaseRecyclerView<Score>() {

    private val scores = mutableListOf<Score>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sport, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ScoreViewHolder) {
            holder.bind(scores[position])
        }
    }

    override fun getItemCount(): Int = scores.size

    fun set(list: List<Score>){
        scores.clear()
        scores.addAll(list)
        notifyDataSetChanged()
    }

    private inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(score: Score) {
            itemView.imgClubOne.loadImage(AGTConstant.PATH_SCORE_CLUB, score.imageClubOne)
            itemView.imgClubTwo.loadImage(AGTConstant.PATH_SCORE_CLUB, score.imageClubTwo)
            itemView.tvNameClubOne.text = score.clubOne
            itemView.tvNameClubTwo.text = score.clubTwo
            itemView.tvScoreClubOne.text = score.scoreClubOne.toString()
            itemView.tvScoreClubTwo.text = score.scoreClubTwo

            if (score.scoreClubOne > score.scoreClubTwo.toInt()) {
                itemView.clubLayoutFirst.setBackgroundResource(R.drawable.bg_club_win_left)
                itemView.clubLayoutSecond.setBackgroundResource(R.drawable.bg_club_lose_left)
            } else {
                itemView.clubLayoutFirst.setBackgroundResource(R.drawable.bg_club_win_right)
                itemView.clubLayoutSecond.setBackgroundResource(R.drawable.bg_club_lose_right)
            }
        }
    }
}