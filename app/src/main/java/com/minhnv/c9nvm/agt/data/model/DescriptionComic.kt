package com.minhnv.c9nvm.agt.data.model

import com.google.gson.annotations.SerializedName

data class DescriptionComic(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("comic_id")
    val comicId: Int
)
