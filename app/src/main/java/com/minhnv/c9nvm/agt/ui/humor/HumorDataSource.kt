package com.minhnv.c9nvm.agt.ui.humor

import androidx.paging.PagingSource
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.model.Humor

class HumorDataSource(
    private val mDataManager: DataManager
) : PagingSource<Int, Humor>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Humor> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = mDataManager.getListHumor(currentLoadingPageKey)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val responseData = mutableListOf<Humor>()
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
            println("#list error: ${e.message}")
            return LoadResult.Error(e)
        }
    }
}