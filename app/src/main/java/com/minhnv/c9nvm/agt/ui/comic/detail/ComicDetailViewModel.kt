package com.minhnv.c9nvm.agt.ui.comic.detail

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
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
    private val saveComicId = MutableLiveData<Int>()
    var listState: Parcelable? = null

    fun loadListComicDetail(comicId: Int): Flow<PagingData<DetailComic>> {
        saveComicId.value = comicId
        return Pager(PagingConfig(10)) {
            ComicDetailDataSource(mDataManager, comicId)
        }.flow.cachedIn(viewModelScope)
    }

    fun findListDetailComic(name: String): Flow<PagingData<DetailComic>> {
        return Pager(PagingConfig(10)) {
            ComicDetailDataSource(mDataManager, saveComicId.value ?: 0, name )
        }.flow.cachedIn(viewModelScope)
    }
}