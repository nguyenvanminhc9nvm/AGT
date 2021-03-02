package com.minhnv.c9nvm.agt.data.model

import com.google.gson.annotations.SerializedName

data class Humor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("create_at")
    val create_at: String
)