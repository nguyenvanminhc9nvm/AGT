package com.minhnv.c9nvm.agt.data.model

import com.google.gson.annotations.SerializedName

data class DetailComic(
    @SerializedName("id")
    val id: Int,
    @SerializedName("comic_id")
    val comicId: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("create_at")
    val createAt: String,
    @SerializedName("name_comic")
    val nameComic: String
)
