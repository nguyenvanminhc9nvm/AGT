package com.minhnv.c9nvm.agt.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {
    override val computation: Scheduler = Schedulers.computation()
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}