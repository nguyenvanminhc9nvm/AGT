package com.minhnv.c9nvm.agt.ui.comic.detail

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicDetailFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainActivity
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicDetailAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_NAME
import com.minhnv.c9nvm.agt.utils.recycler_view.FooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComicDetailFragment :
    BaseFragment<ComicDetailViewModel, ComicDetailFragmentBinding>() {
    private lateinit var comicDetailAdapter: ComicDetailAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDetailFragmentBinding
        get() = ComicDetailFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDetailViewModel> {
        return ComicDetailViewModel::class.java
    }

    override fun initView() {
        linearLayoutManager = LinearLayoutManager(mActivity)
        setHasOptionsMenu(true)
        (mActivity as MainActivity).setSupportActionBar(binding.toolbarDetailComic)
        binding.toolbarDetailComic.title = arguments?.getString(COMIC_NAME) ?: ""
        comicDetailAdapter = ComicDetailAdapter {
            val bundle = Bundle()
            bundle.putInt(AGTConstant.DESCRIPTION_ID, it.comicId)
            activityController.switchFragment(ComicDescriptionFragment(), bundle)
        }
        comicDetailAdapter.withLoadStateFooter(FooterAdapter())
        comicDetailAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (viewModel.listState != null) {
                    binding.rycDetailComic.layoutManager?.onRestoreInstanceState(viewModel.listState)
                }
            }
        })
        binding.rycDetailComic.apply {
            layoutManager = linearLayoutManager
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        val searchManager = mActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    viewModel.findListDetailComic(newText ?: "").collect {
                        comicDetailAdapter.submitData(it)
                    }
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.listState = binding.rycDetailComic.layoutManager?.onSaveInstanceState()
    }
}