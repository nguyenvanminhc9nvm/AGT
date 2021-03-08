package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.data.model.Humor
import io.reactivex.Observable

interface ApiHelper {
    fun getListHumor(page: Int): Observable<List<Humor>>

    fun getListComic(page: Int): Observable<List<Comic>>
}