package com.minhnv.c9nvm.agt.ui.sport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minhnv.c9nvm.agt.data.model.Score
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SportViewModel : BaseViewModel() {
    private val _listScore: MutableLiveData<MutableList<Score>> =
        MutableLiveData()
    val listScore: LiveData<MutableList<Score>>
        get() = _listScore

   fun getListScore() {
       viewModelScope.launch {
           runCatching { mDataManager.getListScore() }
               .onSuccess {
                   _listScore.value = it.body()?.toMutableList() ?: mutableListOf()
               }
       }
   }
}