package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.data.model.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor() : ApiService {

    private val apiService = ApiService.getApiService()

    override suspend fun getListHumor(page: Int): Response<List<Humor>> {
        return apiService.getListHumor(page)
    }

    override suspend fun getListComic(page: Int): Response<List<Comic>> {
        return apiService.getListComic(page)
    }

    override suspend fun getListDetailComic(page: Int, comicId: Int): Response<List<DetailComic>> {
        return apiService.getListDetailComic(page, comicId)
    }

    override suspend fun getListDescriptionComic(
        comicId: Int
    ): Response<List<DescriptionComic>> {
        return apiService.getListDescriptionComic(comicId)
    }

    override suspend fun getListScore(): Response<List<Score>> {
        return apiService.getListScore()
    }

    override suspend fun findListComic(name: String): Response<List<Comic>> {
        return apiService.findListComic(name)
    }
}