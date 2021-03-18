package com.minhnv.c9nvm.agt.ui.comic.data_source

import androidx.paging.PagingSource
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.model.DetailComic

class ComicDetailDataSource(
    private val mDataManager: DataManager,
    private val comicId: Int,
    private val name: String = ""
) : PagingSource<Int, DetailComic>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailComic> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response =  if (name.isEmpty()) {
                mDataManager.getListDetailComic(currentLoadingPageKey, comicId)
            } else {
                mDataManager.findListDetailComic(currentLoadingPageKey, name, comicId)
            }
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val responseData = mutableListOf<DetailComic>()
            val data = response.body()?.toMutableList() ?: mutableListOf()
            responseData.addAll(data)
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (response.body().isNullOrEmpty()) {
                    null
                } else {
                    currentLoadingPageKey.plus(1)
                }
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}