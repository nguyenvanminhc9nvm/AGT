package com.minhnv.c9nvm.agt.ui.comic

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainActivity
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.ComicAdapter
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant
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
        val builder = AdLoader.Builder(mActivity, AGTConstant.UNIT_ID_ADMOB_NATIVE)
            .forUnifiedNativeAd {
                comicAdapter.setAd(it)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    super.onAdFailedToLoad(p0)
                    println("error ads: ${p0?.message}")
                }
            }).build()
        builder.loadAd(AdRequest.Builder().build())
        comicAdapter.withLoadStateFooter(FooterAdapter())
        with(binding.rycComic) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = comicAdapter
        }
        binding.swComic.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.findComic("").collect {
                    comicAdapter.submitData(it)
                }
            }
        }
        comicAdapter.addLoadStateListener {
            binding.swComic.isRefreshing = it.source.refresh is LoadState.Loading
            if (it.prepend is LoadState.Error || it.append is LoadState.Error || it.refresh is LoadState.Error) {
                MaterialAlertDialogBuilder(mActivity)
                    .setTitle(resources.getString(R.string.error_load_list))
                    .setMessage(resources.getString(R.string.error_server))
                    .setNegativeButton(resources.getString(R.string.dismiss)) { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.try_again)) { dialog, which ->
                        comicAdapter.refresh()
                        dialog.dismiss()
                    }
                    .show()
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
                lifecycleScope.launch {
                    viewModel.findComic(query ?: "").collect {
                        comicAdapter.submitData(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
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