package com.minhnv.c9nvm.agt.ui.humor

import android.view.*
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdLoader
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.HumorFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainActivity
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.humor.adapter.HumorAdapter
import com.minhnv.c9nvm.agt.ui.menu.MenuFragment
import com.minhnv.c9nvm.agt.utils.AGTConstant
import com.minhnv.c9nvm.agt.utils.options.SpaceLastItemDecorations
import com.minhnv.c9nvm.agt.utils.recycler_view.FooterAdapter
import io.reactivex.Observable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HumorFragment : BaseFragment<HumorViewModel, HumorFragmentBinding>() {
    private lateinit var humorAdapter: HumorAdapter

    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HumorFragmentBinding
        get() = HumorFragmentBinding::inflate

    override fun createViewModel(): Class<HumorViewModel> {
        return HumorViewModel::class.java
    }

    private var y = 0

    override fun initView() {
        setHasOptionsMenu(true)
        (mActivity as MainActivity).setSupportActionBar(binding.toolbarHumor)
        val builder = AdLoader.Builder(mActivity, AGTConstant.ADMOB_AD_UNIT_ID)
        builder.forNativeAd {
            println("#content ${it.body}")
        }
        humorAdapter = HumorAdapter(mActivity, null)
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
        binding.toolbarHumor.setNavigationOnClickListener {
            activityController.switchFragment(MenuFragment())
        }
        binding.rycHumor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                y = dy
            }
        })
        binding.fabStopScroll.setOnClickListener {
            compositeDisposable.clear()
            Toast.makeText(mActivity, getString(R.string.stop), Toast.LENGTH_SHORT).show()
            binding.fabStopScroll.visibility = View.GONE
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.listHumors.collect {
                humorAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_humor, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.autoScroll) {
            val array = arrayOf(
                "3 second",
                "5 second",
                "7 second",
                "20 second",
                "30 second"
            )
            MaterialAlertDialogBuilder(mActivity)
                .setTitle(getString(R.string.select_time_skip))
                .setNeutralButton(getString(R.string.cancel)) { dig, w ->
                    dig.dismiss()
                }
                .setSingleChoiceItems(array, 1) { dig, pos ->
                    val time = array[pos].filter { it.isDigit() }.toLong()
                    Observable.interval(time, TimeUnit.SECONDS)
                        .observeOn(schedulerProvider.ui)
                        .subscribe {
                            y += 1000
                            println("#pixel: dy $y")
                            binding.rycHumor.postDelayed({
                                binding.rycHumor.smoothScrollBy(0, y)
                            }, 200)
                        }.addToDisposable()
                    binding.fabStopScroll.visibility = View.VISIBLE
                    dig.dismiss()
                }.show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}