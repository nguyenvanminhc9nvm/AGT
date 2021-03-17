package com.minhnv.c9nvm.agt.data.model

import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("id")
    val id: Int,
    @SerializedName("club_one")
    val clubOne: String,
    @SerializedName("score_club_one")
    val scoreClubOne: Int,
    @SerializedName("image_club_one")
    val imageClubOne: String,
    @SerializedName("club_two")
    val clubTwo: String,
    @SerializedName("score_club_two")
    val scoreClubTwo: String,
    @SerializedName("image_club_two")
    val imageClubTwo: String,
    @SerializedName("type")
    val type: Int
)