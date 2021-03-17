package com.minhnv.c9nvm.agt.ui.humor

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel

class HumorViewModel : BaseViewModel() {

    val listHumors = Pager(PagingConfig(3)) {
        HumorDataSource(mDataManager)
    }.flow.cachedIn(viewModelScope)
}