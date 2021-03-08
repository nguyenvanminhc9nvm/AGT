package com.minhnv.c9nvm.agt.ui.comic

import com.minhnv.c9nvm.agt.data.model.Comic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ComicViewModel : BaseViewModel() {

    var page = AGTConstant.PAGE

    data class Input(
        val triggerRefresh: Observable<Boolean>,
        val triggerLoadMore: Observable<Boolean>
    )

    data class Output(
        val comics: Observable<MutableList<Comic>>,
        val error: Observable<String>
    )

    fun transform(input: Input): Output {
        val mComics = PublishSubject.create<MutableList<Comic>>()
        val mError = BehaviorSubject.create<String>()
        val refresh = input.triggerRefresh.filter { it }.map {
            page = 1
        }

        val loadMore = input.triggerLoadMore.filter { it }.map {
            page++
        }

        Observable.merge(refresh, loadMore)
            .switchMap {
                doLoadListComic(page)
            }.delay(2000, TimeUnit.MILLISECONDS).subscribe({
                mComics.onNext(it as MutableList<Comic>)
            }, {
                mError.onNext(it.message ?:"")
            }).addToDisposable()
        return Output(mComics, mError)
    }

    private fun doLoadListComic(page: Int): Observable<List<Comic>> {
        return mDataManager.getListComic(page)
            .subscribeOn(mScheduler.io)
    }
}