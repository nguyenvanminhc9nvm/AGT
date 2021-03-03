package com.minhnv.c9nvm.agt.ui.humor

import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.ui.base.BaseViewModel
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class HumorViewModel : BaseViewModel() {

    var page = AGTConstant.PAGE

    data class Input(
        val triggerRefresh: Observable<Boolean>,
        val triggerLoadMore: Observable<Boolean>
    )

    data class Output(
        val humors: Observable<MutableList<Humor>>
    )

    fun transform(input: Input): Output {
        val mHumors = PublishSubject.create<MutableList<Humor>>()
        val refresh = input.triggerRefresh.filter { it }.map {
            page = 1
        }
        val loadMore = input.triggerLoadMore.filter { it }.map {
            page++
        }

        Observable.merge(refresh, loadMore)
            .switchMap {
                doLoadListHumor(page)
            }.delay(2000, TimeUnit.MILLISECONDS).subscribe ({
                mHumors.onNext(it as MutableList<Humor>)
            }, {

            }).addToDisposable()

        return Output(mHumors)
    }

    private fun doLoadListHumor(page: Int): Observable<List<Humor>> {
        return mDataManager.getListHumor(page)
            .subscribeOn(mScheduler.io)

    }
}