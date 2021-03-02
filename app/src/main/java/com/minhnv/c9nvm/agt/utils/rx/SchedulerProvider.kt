package com.minhnv.c9nvm.agt.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    val computation: Scheduler
    val io: Scheduler
    val ui: Scheduler
}