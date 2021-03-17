package com.minhnv.c9nvm.agt.ui.comic.data_source

import androidx.paging.PagingSource
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.model.Comic

class ComicDataSource(
    private val mDataManager: DataManager
): PagingSource<Int, Comic>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = mDataManager.getListComic(currentLoadingPageKey)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val responseData = mutableListOf<Comic>()
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
            println("#error: ${e.message}")
           return LoadResult.Error(e)
        }
    }
}