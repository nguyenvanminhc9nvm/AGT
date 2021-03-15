package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.data.remote.ApiHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val apiHelper: ApiHelper
): DataManager {
    override fun getListHumor(page: Int): Observable<List<Humor>> {
        return apiHelper.getListHumor(page)
    }

    override fun getListComic(page: Int): Observable<List<Comic>> {
        return apiHelper.getListComic(page)
    }

    override fun getListDetailComic(page: Int, comicId: Int): Observable<List<DetailComic>> {
        return apiHelper.getListDetailComic(page, comicId)
    }

    override fun getListDescriptionComic(
        comicId: Int
    ): Observable<List<DescriptionComic>> {
        return apiHelper.getListDescriptionComic(comicId)
    }
}