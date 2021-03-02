package com.minhnv.c9nvm.agt.utils.tracking

import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.Callable

/**
 * Created by OpenYourEyes on 3/3/2020
 */
class RxProgressBar(schedulerProvider: SchedulerProvider) : Observable<Boolean>() {

    private val variable = BehaviorSubject.createDefault(0)

    private var loading: Observable<Boolean>

    init {
        loading = variable
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.ui)
            .onErrorReturnItem(0)
            .map { x -> x > 0 }
            .share()
    }

    fun <T> trackActivity(source: Observable<T>): Observable<T> {
        println("trackActivity")
        val resourceFactory: Callable<Unit> = Callable {
            increment()
            Unit.default
        }

        val observableFactory = Function<Unit, Observable<T>> { source }
        val disposer = Consumer<Unit> { decrement() }
        return using(resourceFactory, observableFactory, disposer)
    }

    override fun subscribeActual(observer: Observer<in Boolean>) {
        loading.subscribe(observer)
    }

    private fun increment() {
        println("increment")
        variable.onNext((variable.value ?: 0) + 1)
    }

    private fun decrement() {
        println("decrement")
        variable.onNext((variable.value ?: 0) - 1)
    }

    fun reset() {
        variable.onNext(0)
    }
}

fun <T> Observable<T>.trackProgressBar(trackIndicator: RxProgressBar): Observable<T> {
    return trackIndicator.trackActivity(this)
}


class Unit : Comparable<Unit> {

    companion object {
        val default = Unit()
    }

    override fun compareTo(other: Unit): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        return other is Unit
    }

    override fun hashCode(): Int {
        return 0
    }
}
