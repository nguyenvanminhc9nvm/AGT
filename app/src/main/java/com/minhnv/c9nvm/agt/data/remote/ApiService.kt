package com.minhnv.c9nvm.agt.data.remote

import com.minhnv.c9nvm.agt.BuildConfig
import com.minhnv.c9nvm.agt.data.model.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiEndPoint.ENDPOINT_HUMOR)
    suspend fun getListHumor(@Query("page") page: Int): Response<List<Humor>>

    @GET(ApiEndPoint.ENDPOINT_COMIC)
    suspend fun getListComic(@Query("page") page: Int): Response<List<Comic>>

    @GET(ApiEndPoint.ENDPOINT_DETAIL_COMIC)
    suspend fun getListDetailComic(
        @Query("page") page: Int,
        @Query("comicId") comicId: Int
    ): Response<List<DetailComic>>

    @GET(ApiEndPoint.ENDPOINT_DESCRIPTION_COMIC)
    suspend fun getListDescriptionComic(
        @Query("comicId") comicId: Int
    ): Response<List<DescriptionComic>>

    @GET(ApiEndPoint.ENDPOINT_SCORE)
    suspend fun getListScore(): Response<List<Score>>

    @GET(ApiEndPoint.ENDPOINT_FIND_COMIC)
    suspend fun findListComic(@Query("name") name: String): Response<List<Comic>>

    companion object {
        fun getApiService(): ApiService = Retrofit.Builder()
            .baseUrl(BuildConfig.API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
