package com.minhnv.c9nvm.agt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.ui.MainViewModel
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.ui.comic.ComicViewModel
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailViewModel
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionViewModel
import com.minhnv.c9nvm.agt.ui.humor.HumorViewModel
import com.minhnv.c9nvm.agt.ui.main.AGTViewModel
import com.minhnv.c9nvm.agt.ui.sport.SportViewModel
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelProviderFactory @Inject constructor(
        private val dataManager: DataManager,
        private val schedulerProvider: SchedulerProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(ComicViewModel::class.java) -> ComicViewModel() as T
            modelClass.isAssignableFrom(SportViewModel::class.java) -> SportViewModel() as T
            modelClass.isAssignableFrom(HumorViewModel::class.java) -> HumorViewModel() as T
            modelClass.isAssignableFrom(ComicDetailViewModel::class.java) -> ComicDetailViewModel() as T
            modelClass.isAssignableFrom(AGTViewModel::class.java) -> AGTViewModel() as T
            modelClass.isAssignableFrom(ComicDescriptionViewModel::class.java) -> ComicDescriptionViewModel() as T
            else -> throw Exception("no viewModel")
        }

        if (viewModel is BaseViewModel) {
            viewModel.initData(dataManager, schedulerProvider)
        }
        return viewModel
    }
}