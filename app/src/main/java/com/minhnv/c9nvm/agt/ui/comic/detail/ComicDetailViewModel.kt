package com.minhnv.c9nvm.agt.ui.comic.detail

import com.minhnv.c9nvm.agt.data.model.DetailComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class ComicDetailViewModel : BaseViewModel() {
    var page = AGTConstant.PAGE

    data class Input(
        val comicId: Observable<Int>,
        val triggerRefresh: Observable<Boolean>,
        val triggerLoadMore: Observable<Boolean>
    )

    data class Output(
        val detailComics: Observable<MutableList<DetailComic>>,
        val error: Observable<String>
    )

    fun transform(input: Input): Output {
        val mComicId = BehaviorSubject.create<Int>()
        val mComics = PublishSubject.create<MutableList<DetailComic>>()
        val mError = BehaviorSubject.create<String>()
        input.comicId.subscribe(mComicId)
        val refresh = input.triggerRefresh.filter { it }.map {
            page = 1
        }

        val loadMore = input.triggerLoadMore.filter { it }.map {
            page++
        }

        Observable.merge(refresh, loadMore)
            .switchMap {
                doLoadListDetailComic(page, mComicId.value ?: 0)
            }.subscribe({
                mComics.onNext(it as MutableList<DetailComic>)
            }, {
                mError.onNext(it.message ?: "")
            }).addToDisposable()
        return Output(mComics, mError)
    }

    private fun doLoadListDetailComic(page: Int, comicId: Int): Observable<List<DetailComic>> {
        return mDataManager.getListDetailComic(page, comicId)
            .subscribeOn(mScheduler.io)
    }
}