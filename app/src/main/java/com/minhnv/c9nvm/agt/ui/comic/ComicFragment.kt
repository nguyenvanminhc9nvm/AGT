package com.minhnv.c9nvm.agt.ui.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject

class ComicFragment : BaseFragment<ComicViewModel, ComicFragmentBinding>(), PageIndicator {
    private lateinit var comicAdapter: ComicAdapter

    override fun createViewModel(): Class<ComicViewModel> {
        return ComicViewModel::class.java
    }

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicFragmentBinding
        get() = ComicFragmentBinding::inflate


    override fun initView() {
        val bundle = Bundle()
        comicAdapter = ComicAdapter(mActivity) {
            bundle.putInt(COMIC_ID, it.id)
            activityController.switchFragment(
                fragmentId = ComicDetailFragment(),
                bundle = bundle,
                isAdd = true
            )
        }
        viewModel.transform(
            ComicViewModel.Input(
                triggerRefresh, triggerLoadMore
            )
        ).apply {
            comics.observeOn(schedulerProvider.ui).subscribe {
                binding.rycComic.checkEndOfPage(it)
                comicAdapter.updateList(it)
            }.addToDisposable()
            error.observeOn(schedulerProvider.ui).subscribe {
                Toast.makeText(mActivity, "error $it", Toast.LENGTH_SHORT).show()
            }.addToDisposable()
        }
    }

    override fun bindViewModel() {
        with(binding.rycComic) {
            layoutManager = GridLayoutManager(mActivity, 2)
            setHasFixedSize(true)
            adapter = comicAdapter
            initLoadMore(binding.swComic, this@ComicFragment)
        }
    }

    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()


}