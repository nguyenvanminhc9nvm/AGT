package com.minhnv.c9nvm.agt.ui.comic.detail.description

import com.minhnv.c9nvm.agt.data.model.DescriptionComic
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class ComicDescriptionViewModel : BaseViewModel() {
    var page = AGTConstant.PAGE

    data class Input(
        val comicId: Observable<Int>
    )

    data class Output(
        val detailComics: Observable<MutableList<DescriptionComic>>,
        val error: Observable<String>
    )

    fun transform(input: Input): Output {
        val mComicId = BehaviorSubject.create<Int>()
        val mComics = PublishSubject.create<MutableList<DescriptionComic>>()
        val mError = BehaviorSubject.create<String>()
        input.comicId.subscribe(mComicId)
        doLoadListDetailComic(mComicId.value ?: 0)
            .subscribe({
                mComics.onNext(it as MutableList<DescriptionComic>)
            }, {
                mError.onNext(it.message ?: "")
            }).addToDisposable()
        return Output(mComics, mError)
    }

    private fun doLoadListDetailComic(comicId: Int): Observable<List<DescriptionComic>> {
        return mDataManager.getListDescriptionComic(comicId)
            .subscribeOn(mScheduler.io)
    }
}