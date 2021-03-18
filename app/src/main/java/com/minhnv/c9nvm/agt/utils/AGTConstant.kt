package com.minhnv.c9nvm.agt.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.minhnv.c9nvm.agt.BuildConfig

object AGTConstant {
    const val PAGE = 1
    const val PATH_HUMOR = "${BuildConfig.API_HOST}uploads/"
    const val PATH_COMIC = "${BuildConfig.API_HOST}uploads/comic/image/"
    const val PATH_DETAIL_COMIC = "${BuildConfig.API_HOST}uploads/comic/image/detail/"
    const val PATH_DESCRIPTION_COMIC = "${BuildConfig.API_HOST}uploads/comic/image/detail/description/"
    const val PATH_SCORE_CLUB = "${BuildConfig.API_HOST}uploads/sport/"
    const val COMIC_ID = "COMIC_ID"
    const val COMIC_NAME = "COMIC_NAME"
    const val DESCRIPTION_ID = "DESCRIPTION_ID"
    const val STATE_LIST = "STATE_LIST"
    const val ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
}

val Context.dataStore : DataStore<Preferences> by preferencesDataStore("preferencesKey")