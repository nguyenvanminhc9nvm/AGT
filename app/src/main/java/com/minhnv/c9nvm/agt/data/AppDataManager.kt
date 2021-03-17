package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.model.*
import com.minhnv.c9nvm.agt.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val apiHelper: ApiService
): DataManager {
    override suspend fun getListHumor(page: Int): Response<List<Humor>> {
        return apiHelper.getListHumor(page)
    }

    override suspend fun getListComic(page: Int): Response<List<Comic>> {
        return apiHelper.getListComic(page)
    }

    override suspend fun getListDetailComic(page: Int, comicId: Int): Response<List<DetailComic>> {
        return apiHelper.getListDetailComic(page, comicId)
    }

    override suspend fun getListDescriptionComic(
        comicId: Int
    ): Response<List<DescriptionComic>> {
        return apiHelper.getListDescriptionComic(comicId)
    }

    override suspend fun getListScore(): Response<List<Score>> {
        return apiHelper.getListScore()
    }

    override suspend fun findListComic(name: String): Response<List<Comic>> {
        return apiHelper.findListComic(name)
    }
}

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified T> DataManager.getList(page: Int, comicId: Int?): MutableList<T> {
    return when(T::class.java) {
        Humor::class.java -> getListHumor(page).body() as MutableList<T>
        Comic::class.java -> getListComic(page).body() as MutableList<T>
        DetailComic::class.java -> getListDetailComic(page, comicId!!).body() as MutableList<T>
        DescriptionComic::class.java -> getListDescriptionComic(comicId!!).body() as MutableList<T>
        Score::class.java -> getListScore().body() as MutableList<T>
        else -> mutableListOf()
    }
}