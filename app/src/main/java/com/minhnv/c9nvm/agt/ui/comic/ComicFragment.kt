package com.minhnv.c9nvm.agt.ui.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.recycler_view.FooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicFragment : BaseFragment<ComicViewModel, ComicFragmentBinding>() {
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
                bundle = bundle
            )
        }
        comicAdapter.withLoadStateFooter(FooterAdapter())
        with(binding.rycComic) {
            layoutManager = GridLayoutManager(mActivity, 2)
            setHasFixedSize(true)
            adapter = comicAdapter
        }
        binding.swComic.setOnRefreshListener {
            comicAdapter.refresh()
        }
        comicAdapter.addLoadStateListener {
            binding.swComic.isRefreshing = it.source.refresh is LoadState.Loading
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.listComic.collect {
                comicAdapter.submitData(it)
            }
        }
    }

}