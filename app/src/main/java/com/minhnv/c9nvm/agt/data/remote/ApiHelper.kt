package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.data.model.*
import io.reactivex.Observable

interface ApiHelper {
    suspend fun getListHumor(page: Int): Observable<List<Humor>>

    fun getListComic(page: Int): Observable<List<Comic>>

    fun getListDetailComic(page: Int, comicId: Int): Observable<List<DetailComic>>

    fun getListDescriptionComic(comicId: Int): Observable<List<DescriptionComic>>

    fun getListScore(): Observable<List<Score>>

    fun findListComic(name: String): Observable<List<Comic>>
}