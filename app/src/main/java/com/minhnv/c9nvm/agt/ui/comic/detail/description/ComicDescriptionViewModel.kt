package com.minhnv.c9nvm.agt.ui.comic.detail.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class ComicDescriptionViewModel : BaseViewModel() {
    private val _listComicDescription = MutableLiveData<MutableList<DescriptionComic>>()
    val listComicDescription get() = _listComicDescription

    fun getListComicDescription(comicId: Int) {
        viewModelScope.launch {
            runCatching {
                mDataManager.getListDescriptionComic(comicId)
            }.onSuccess {
                _listComicDescription.value = it.body()?.toMutableList()
            }
        }
    }

    private val _currentPage = MutableLiveData(0)
    val currentPage: LiveData<Int> get() = _currentPage

    private val _listTimeSkip = MutableStateFlow(
        arrayOf(
            "10 second",
            "20 second",
            "30 second"
        )
    )
    val listTimeSkip get() = _listTimeSkip

    fun savePageWithComicId(comicId: Int, page: Int) {
        viewModelScope.launch {
            mDataManager.insertPage(comicId, page)
        }
    }

    fun getPageWithComicId(comicId: Int): Flow<Int> {
        return flow {
            runCatching {
                mDataManager.getPage(comicId)
            }.onSuccess {
                emit(it)
            }
        }.take(1)
    }
}