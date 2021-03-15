package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.data.model.Humor
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor() : ApiHelper {
    override fun getListHumor(page: Int): Observable<List<Humor>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_HUMOR)
            .addPathParameter("page", page.toString())
            .build()
            .getObjectListObservable(Humor::class.java)
    }

    override fun getListComic(page: Int): Observable<List<Comic>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_COMIC)
            .addPathParameter("page", page.toString())
            .build()
            .getObjectListObservable(Comic::class.java)
    }

    override fun getListDetailComic(page: Int, comicId: Int): Observable<List<DetailComic>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DETAIL_COMIC)
            .addPathParameter("page", page.toString())
            .addPathParameter("comicId", comicId.toString())
            .build()
            .getObjectListObservable(DetailComic::class.java)
    }

    override fun getListDescriptionComic(
        comicId: Int
    ): Observable<List<DescriptionComic>> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_DESCRIPTION_COMIC)
            .addPathParameter("comicId", comicId.toString())
            .build()
            .getObjectListObservable(DescriptionComic::class.java)
    }
}