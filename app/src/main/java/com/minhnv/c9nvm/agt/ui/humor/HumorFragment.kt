package com.minhnv.c9nvm.agt.ui.humor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.HumorFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.humor.adapter.HumorAdapter
import com.minhnv.c9nvm.agt.utils.options.SpaceLastItemDecorations
import com.minhnv.c9nvm.agt.utils.recycler_view.FooterAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HumorFragment : BaseFragment<HumorViewModel, HumorFragmentBinding>() {
    private lateinit var humorAdapter: HumorAdapter

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HumorFragmentBinding
        get() = HumorFragmentBinding::inflate

    override fun createViewModel(): Class<HumorViewModel> {
        return HumorViewModel::class.java
    }

    override fun initView() {
        humorAdapter = HumorAdapter(mActivity)
        humorAdapter.withLoadStateFooter(footer = FooterAdapter())
        with(binding.rycHumor) {
            layoutManager = LinearLayoutManager(mActivity)
            setHasFixedSize(true)
            adapter = humorAdapter
            addItemDecoration(SpaceLastItemDecorations())
        }
        binding.swHumor.setOnRefreshListener {
            humorAdapter.refresh()
        }
        humorAdapter.addLoadStateListener {
            binding.swHumor.isRefreshing = it.source.refresh is LoadState.Loading
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.listHumors.collect {
                humorAdapter.submitData(it)
            }
        }
    }
}