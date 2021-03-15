package com.minhnv.c9nvm.agt.ui.comic.detail.description

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.ComicDescriptionFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.DescriptionComicAdapter
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject

class ComicDescriptionFragment :
    BaseFragment<ComicDescriptionViewModel, ComicDescriptionFragmentBinding>(), PageIndicator {
    private lateinit var descriptionAdapter: DescriptionComicAdapter
    private val mComicId = BehaviorSubject.create<Int>()
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDescriptionFragmentBinding
        get() = ComicDescriptionFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDescriptionViewModel> {
        return ComicDescriptionViewModel::class.java
    }

    override fun initView() {
        descriptionAdapter = DescriptionComicAdapter()
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        mComicId.onNext(comicId ?: 0)
        val output = viewModel.transform(ComicDescriptionViewModel.Input(mComicId))
        output.detailComics.observeOn(schedulerProvider.ui).subscribe {
            descriptionAdapter.set(it)
        }.addToDisposable()

    }

    override fun bindViewModel() {
        binding.vpDescriptionComic.adapter = descriptionAdapter
    }

    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()


}