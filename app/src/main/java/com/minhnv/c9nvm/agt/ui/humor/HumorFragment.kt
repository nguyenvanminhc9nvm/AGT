package com.minhnv.c9nvm.agt.ui.humor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhnv.c9nvm.agt.databinding.HumorFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.humor.adapter.HumorAdapter
import com.minhnv.c9nvm.agt.utils.options.SpaceLastItemDecorations
import com.minhnv.c9nvm.agt.utils.recycler_view.PageIndicator
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.humor_fragment.*

class HumorFragment : BaseFragment<HumorViewModel, HumorFragmentBinding>(), PageIndicator {
    private lateinit var humorAdapter: HumorAdapter
    override var triggerLoadMore: BehaviorSubject<Boolean> = BehaviorSubject.create()
    override var triggerRefresh: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HumorFragmentBinding
        get() = HumorFragmentBinding::inflate

    override fun createViewModel(): Class<HumorViewModel> {
        return HumorViewModel::class.java
    }

    override fun initView() {
        humorAdapter = HumorAdapter(mActivity)

        val output = viewModel.transform(
            HumorViewModel.Input(
                triggerRefresh, triggerLoadMore
            )
        )
        output.humors.observeOn(schedulerProvider.ui).subscribe {
            humorAdapter.updateList(it)
            binding.rycHumor.checkEndOfPage(it)
        }.addToDisposable()
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
}