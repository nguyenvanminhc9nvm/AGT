package com.minhnv.c9nvm.agt.ui.base

import androidx.lifecycle.ViewModel
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import com.minhnv.c9nvm.agt.utils.tracking.RxProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    lateinit var mDataManager: DataManager

    lateinit var mScheduler: SchedulerProvider


    val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val mProgressBar: RxProgressBar by lazy {
        RxProgressBar(mScheduler)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }

    fun Disposable.addToDisposable() {
        mCompositeDisposable.add(this)
    }

    fun onDestroyView() {
        mCompositeDisposable.clear()
    }


    fun initData(dataManager: DataManager, scheduler: SchedulerProvider) {
        this.mDataManager = dataManager
        this.mScheduler = scheduler
    }

}