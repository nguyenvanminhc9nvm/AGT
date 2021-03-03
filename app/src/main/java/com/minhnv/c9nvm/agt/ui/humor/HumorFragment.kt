package com.minhnv.c9nvm.agt.ui.humor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.HumorFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.humor.adapter.HumorAdapter
import com.minhnv.c9nvm.agt.utils.options.SpaceLastItemDecorations
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject

class HumorFragment : BaseFragment<HumorViewModel, HumorFragmentBinding>(), PageIndicator {
    private lateinit var humorAdapter: HumorAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HumorFragmentBinding
        get() = HumorFragmentBinding::inflate

    override fun createViewModel(): Class<HumorViewModel> {
        return HumorViewModel::class.java
    }

    override fun initView() {
        humorAdapter = HumorAdapter(mActivity)
        viewModel.transform(HumorViewModel.Input(
            triggerRefresh, triggerLoadMore
        )).apply {
            humors.observeOn(schedulerProvider.ui).subscribe {
                binding.rycHumor.checkEndOfPage(it)
                humorAdapter.updateList(it)
            }.addToDisposable()
        }
    }

    override fun bindViewModel() {
        with(binding.rycHumor) {
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = humorAdapter
            addItemDecoration(SpaceLastItemDecorations())
            initLoadMore(binding.swHumor, this@HumorFragment)
        }

    }

    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()


}