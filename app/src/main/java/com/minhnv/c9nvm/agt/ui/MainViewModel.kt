package com.minhnv.c9nvm.agt.ui

import androidx.lifecycle.viewModelScope
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    fun saveFirstSignInApp(isFirst: Boolean) {
        viewModelScope.launch {
            mDataManager.saveFirstTimeSeeAdMob(isFirst)
        }
    }
}