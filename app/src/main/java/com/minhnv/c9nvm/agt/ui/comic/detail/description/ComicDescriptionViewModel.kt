package com.minhnv.c9nvm.agt.ui.comic.detail.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _currentPage = MutableStateFlow(0)
    val currentPage : StateFlow<Int> get() = _currentPage

    private val _listTimeSkip = MutableStateFlow(arrayOf(
        "10 second",
        "20 second",
        "30 second"
    ))
    val listTimeSkip get() = _listTimeSkip

}