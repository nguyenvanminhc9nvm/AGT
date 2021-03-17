package com.minhnv.c9nvm.agt.utils.options

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minhnv.c9nvm.agt.R

fun ImageView.loadImage(basePath: String, urlImage: String?) {
    Glide.with(this).load(basePath + urlImage).placeholder(R.drawable.bg_error)
        .error(R.drawable.bg_error).into(this)
}

@BindingAdapter("imageUrl")
fun loadImages(imageView: ImageView, urlImage: String?) {
    Glide.with(imageView).load(urlImage).placeholder(R.drawable.bg_error)
        .error(R.drawable.bg_error).into(imageView)
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