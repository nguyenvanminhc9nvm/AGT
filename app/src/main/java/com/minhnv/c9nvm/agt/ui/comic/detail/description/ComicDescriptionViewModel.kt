package com.minhnv.c9nvm.agt.ui.comic.detail.description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
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
}