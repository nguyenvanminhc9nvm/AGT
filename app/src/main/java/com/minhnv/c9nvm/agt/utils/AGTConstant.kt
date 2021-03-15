package com.minhnv.c9nvm.agt.utils

import com.minhnv.c9nvm.agt.BuildConfig

object AGTConstant {
    const val PAGE = 1
    const val PATH_HUMOR = "${BuildConfig.API_HOST}/uploads/"
    const val PATH_COMIC = "${BuildConfig.API_HOST}/uploads/comic/image/"
    const val PATH_DETAIL_COMIC = "${BuildConfig.API_HOST}/uploads/comic/image/detail/"
    const val PATH_DESCRIPTION_COMIC = "${BuildConfig.API_HOST}/uploads/comic/image/detail/description/"
    const val COMIC_ID = "COMIC_ID"
    const val DESCRIPTION_ID = "DESCRIPTION_ID"
}