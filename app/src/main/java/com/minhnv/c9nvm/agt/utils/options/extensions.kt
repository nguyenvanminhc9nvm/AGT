package com.minhnv.c9nvm.agt.utils.options

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minhnv.c9nvm.agt.R

fun ImageView.loadImage(urlImage: String?) {
    Glide.with(this).load(urlImage).placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background).into(this)
}


/**
 * this class create spacing in last item of list
 * **/
class SpaceLastItemDecorations : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.bottom = 20
        }
    }
}