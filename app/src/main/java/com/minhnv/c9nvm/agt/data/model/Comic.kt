package com.minhnv.c9nvm.agt.data.model

import com.google.gson.annotations.SerializedName
import com.minhnv.c9nvm.agt.utils.AGTConstant

data class Comic(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_image")
    val nameComic: String,
    @SerializedName("comic_image")
    val comicImage: String,
    @SerializedName("create_at")
    val createAt: String
) {
    fun pathComic(): String {
        return AGTConstant.PATH_COMIC + comicImage
    }


}