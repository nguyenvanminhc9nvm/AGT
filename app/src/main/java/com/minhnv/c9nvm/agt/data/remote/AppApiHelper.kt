package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.data.model.*
import com.minhnv.c9nvm.agt.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ApiService {

    private val apiService = ApiService.getApiService()

    override suspend fun getListHumor(page: Int): Response<List<Humor>> {
        return withContext(ioDispatcher) { apiService.getListHumor(page) }
    }

    override suspend fun getListComic(page: Int): Response<List<Comic>> {
        return withContext(ioDispatcher) { apiService.getListComic(page) }
    }

    override suspend fun getListDetailComic(page: Int, comicId: Int): Response<List<DetailComic>> {
        return withContext(ioDispatcher) { apiService.getListDetailComic(page, comicId) }
    }

    override suspend fun getListDescriptionComic(
        comicId: Int
    ): Response<List<DescriptionComic>> {
        return withContext(ioDispatcher) { apiService.getListDescriptionComic(comicId) }
    }

    override suspend fun getListScore(): Response<List<Score>> {
        return withContext(ioDispatcher) { apiService.getListScore() }
    }

    override suspend fun findListComic(name: String, page: Int): Response<List<Comic>> {
        return withContext(ioDispatcher) { apiService.findListComic(name, page) }
    }

    override suspend fun findListDetailComic(
        page: Int,
        name: String,
        comicId: Int
    ): Response<List<DetailComic>> {
        return withContext(ioDispatcher) { apiService.findListDetailComic(page, name, comicId) }
    }
}