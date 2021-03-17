package com.minhnv.c9nvm.agt.ui.comic.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.ComicDetailFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicDetailAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.recycler_view.FooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicDetailFragment :
    BaseFragment<ComicDetailViewModel, ComicDetailFragmentBinding>() {
    private lateinit var comicDetailAdapter: ComicDetailAdapter

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDetailFragmentBinding
        get() = ComicDetailFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDetailViewModel> {
        return ComicDetailViewModel::class.java
    }

    override fun initView() {
        comicDetailAdapter = ComicDetailAdapter {
            val bundle = Bundle()
            bundle.putInt(AGTConstant.DESCRIPTION_ID, it.comicId)
            activityController.switchFragment(ComicDescriptionFragment(), bundle)
        }
        comicDetailAdapter.withLoadStateFooter(FooterAdapter())
        binding.rycDetailComic.apply {
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = comicDetailAdapter
        }
        binding.swDetailComic.setOnRefreshListener {
            comicDetailAdapter.refresh()
        }
        comicDetailAdapter.addLoadStateListener {
            binding.swDetailComic.isRefreshing = it.source.refresh is LoadState.Loading
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            val comicId = arguments?.getInt(COMIC_ID)
            viewModel.loadListComicDetail(comicId ?: 0).collect {
                comicDetailAdapter.submitData(it)
            }
        }
    }
}