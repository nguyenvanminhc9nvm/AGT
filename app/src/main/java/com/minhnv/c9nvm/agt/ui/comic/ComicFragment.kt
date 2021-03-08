package com.minhnv.c9nvm.agt.ui.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicAdapter
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject

class ComicFragment : BaseFragment<ComicViewModel, ComicFragmentBinding>(), PageIndicator {
    private lateinit var comicAdapter: ComicAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicFragmentBinding
        get() = ComicFragmentBinding::inflate

    override fun createViewModel(): Class<ComicViewModel> {
        return ComicViewModel::class.java
    }

    override fun initView() {
        comicAdapter = ComicAdapter(mActivity)
        viewModel.transform(
            ComicViewModel.Input(
            triggerRefresh, triggerLoadMore
        )).apply {
            comics.observeOn(schedulerProvider.ui).subscribe {
                binding.rycComic.checkEndOfPage(it)
                comicAdapter.updateList(it)
            }.addToDisposable()
            error.observeOn(schedulerProvider.ui).subscribe {

            }.addToDisposable()
        }
    }

    override fun bindViewModel() {
        binding.rycComic.apply {
            layoutManager = GridLayoutManager(mActivity, 2)
            setHasFixedSize(true)
            adapter = comicAdapter
            initLoadMore(binding.swComic, this@ComicFragment)
        }
    }

    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()


}