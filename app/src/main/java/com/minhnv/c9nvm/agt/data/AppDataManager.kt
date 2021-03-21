package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.local.db.DBHelper
import com.minhnv.c9nvm.agt.data.model.*
import com.minhnv.c9nvm.agt.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val apiHelper: ApiService,
    private val dataStoreHelper: DataStoreHelper,
    private val dbHelper: DBHelper
): DataManager {
    override suspend fun saveFirstTimeSeeAdMob(isFirst: Boolean) {
        return dataStoreHelper.saveFirstTimeSeeAdMob(isFirst)
    }

    override val isFirstTimeSeeAdMob: Flow<Boolean>
        get() = dataStoreHelper.isFirstTimeSeeAdMob

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

    override suspend fun findListComic(name: String, page: Int): Response<List<Comic>> {
        return apiHelper.findListComic(name, page)
    }

    override suspend fun findListDetailComic(
        page: Int,
        name: String,
        comicId: Int
    ): Response<List<DetailComic>> {
        return apiHelper.findListDetailComic(page, name, comicId)
    }

    override suspend fun insertPage(comicId: Int, page: Int) {
        return dbHelper.insertPage(comicId, page)
    }

    override suspend fun getPage(comicId: Int): Int {
        return dbHelper.getPage(comicId)
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