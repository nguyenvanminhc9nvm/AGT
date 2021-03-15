package com.minhnv.c9nvm.agt.ui.comic.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.ComicDetailFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicDetailAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject

class ComicDetailFragment :
    BaseFragment<ComicDetailViewModel, ComicDetailFragmentBinding>(), PageIndicator {
    private lateinit var comicDetailAdapter: ComicDetailAdapter
    private val mComicId = BehaviorSubject.create<Int>()

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDetailFragmentBinding
        get() = ComicDetailFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDetailViewModel> {
        return ComicDetailViewModel::class.java
    }

    override fun initView() {
        val comicId = arguments?.getInt(COMIC_ID)
        mComicId.onNext(comicId ?: 0)
        viewModel.transform(
            ComicDetailViewModel.Input(
                mComicId,
                triggerRefresh,
                triggerLoadMore
            )
        ).apply {
            detailComics.observeOn(schedulerProvider.ui).subscribe {
                binding.rycDetailComic.checkEndOfPage(it)
                comicDetailAdapter.updateList(it)
            }.addToDisposable()
            error.observeOn(schedulerProvider.ui).subscribe {
                Toast.makeText(mActivity, "error $it", Toast.LENGTH_SHORT).show()
            }.addToDisposable()
        }
    }

    override fun bindViewModel() {
        comicDetailAdapter = ComicDetailAdapter(mActivity) {
            val bundle = Bundle()
            bundle.putInt(AGTConstant.DESCRIPTION_ID, it.comicId)
           activityController.switchFragment(ComicDescriptionFragment(), bundle)
        }
        binding.rycDetailComic.apply {
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = comicDetailAdapter
            initLoadMore(binding.swDetailComic, this@ComicDetailFragment)
        }
    }

    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()

}