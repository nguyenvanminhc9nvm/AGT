package com.minhnv.c9nvm.agt.ui.comic

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.ui.comic.data_source.ComicDataSource
import kotlinx.coroutines.flow.Flow

class ComicViewModel : BaseViewModel() {

    val listComic = Pager(PagingConfig(6)) {
        ComicDataSource(mDataManager)
    }.flow.cachedIn(viewModelScope)

    fun findComic(name: String): Flow<PagingData<Comic>> {
        return Pager(PagingConfig(10)) {
            ComicDataSource(mDataManager, name)
        }.flow.cachedIn(viewModelScope)
    }
}