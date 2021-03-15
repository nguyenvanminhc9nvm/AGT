package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.BuildConfig

object ApiEndPoint {
    const val ENDPOINT_HUMOR = BuildConfig.API_HOST + "/api/getListHumor.php?page={page}"
    const val ENDPOINT_COMIC = BuildConfig.API_HOST + "/api/getListComic.php?page={page}"
    const val ENDPOINT_DETAIL_COMIC =
        BuildConfig.API_HOST + "/api/getListDetailComic.php?page={page}&comicId={comicId}"
    const val ENDPOINT_DESCRIPTION_COMIC =
        BuildConfig.API_HOST + "/api/getDescriptionComic.php?page={page}&comicId={comicId}"
}