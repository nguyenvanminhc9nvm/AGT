package com.minhnv.c9nvm.agt.ui.comic.detail

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.ui.comic.data_source.ComicDetailDataSource
import kotlinx.coroutines.flow.Flow

class ComicDetailViewModel : BaseViewModel() {
    fun loadListComicDetail(comicId: Int): Flow<PagingData<DetailComic>> {
        return Pager(PagingConfig(10)) {
            ComicDetailDataSource(mDataManager, comicId)
        }.flow.cachedIn(viewModelScope)
    }
}