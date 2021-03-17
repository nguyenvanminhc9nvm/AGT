package com.minhnv.c9nvm.agt.utils.recycler_view

import androidx.paging.PagingSource
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.getList

class DataSourceLibrary<T : Any>(
    private val mDataManager: DataManager,
    private val comicId: Int? = null,
) : PagingSource<Int, T>() {
    @Suppress("UNCHECKED_CAST")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = mDataManager.getList<Class<T>>(currentLoadingPageKey, comicId)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val responseData = mutableListOf<T>()
            val data = (response as Collection<T>).toMutableList()
            responseData.addAll(data)
            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = if (response.isNullOrEmpty()) {
                    null
                } else {
                    currentLoadingPageKey.plus(1)
                }
            )
        } catch (e: Exception) {
            println("#list error: ${e.message}")
            return LoadResult.Error(e)
        }
    }
}
