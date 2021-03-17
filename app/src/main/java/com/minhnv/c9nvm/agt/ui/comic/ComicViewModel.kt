package com.minhnv.c9nvm.agt.ui.comic

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.ui.comic.data_source.ComicDataSource

class ComicViewModel : BaseViewModel() {
    val listComic = Pager(PagingConfig(6)) {
        ComicDataSource(mDataManager)
    }.flow.cachedIn(viewModelScope)
}