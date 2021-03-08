package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.BuildConfig

object ApiEndPoint {
    const val ENDPOINT_HUMOR = BuildConfig.API_HOST + "/api/getListHumor.php"
    const val ENDPOINT_COMIC = BuildConfig.API_HOST + "/api/getListComic.php"
}