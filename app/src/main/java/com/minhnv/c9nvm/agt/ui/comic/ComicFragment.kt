package com.minhnv.c9nvm.agt.ui.comic

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainActivity
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_ID
import com.minhnv.c9nvm.agt.utils.AGTConstant.COMIC_NAME
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
        setHasOptionsMenu(true)
        (mActivity as MainActivity).setSupportActionBar(binding.toolbarComic)
        val bundle = Bundle()
        comicAdapter = ComicAdapter(mActivity) {
            bundle.putInt(COMIC_ID, it.id)
            bundle.putString(COMIC_NAME, it.nameComic)
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
                    viewModel.findComic(newText ?: "").collect {
                        comicAdapter.submitData(it)
                    }
                }
                return true
            }
        })
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.listComic.collect {
                comicAdapter.submitData(it)
            }
        }
    }

}