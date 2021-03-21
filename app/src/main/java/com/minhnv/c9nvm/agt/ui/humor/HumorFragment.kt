package com.minhnv.c9nvm.agt.ui.humor

import android.view.*
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
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
        humorAdapter = HumorAdapter(mActivity)
        val builder = AdLoader.Builder(mActivity, AGTConstant.UNIT_ID_ADMOB_NATIVE)
            .forUnifiedNativeAd {
               humorAdapter.setAd(it)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    super.onAdFailedToLoad(p0)
                    println("error ads: ${p0?.message}")
                }
            }).build()
        builder.loadAd(AdRequest.Builder().build())

        (mActivity as MainActivity).setSupportActionBar(binding.toolbarHumor)
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
            if (it.prepend is LoadState.Error || it.append is LoadState.Error || it.refresh is LoadState.Error) {
                MaterialAlertDialogBuilder(mActivity)
                    .setTitle(resources.getString(R.string.error_load_list))
                    .setMessage(resources.getString(R.string.error_server))
                    .setNegativeButton(resources.getString(R.string.dismiss)) { dialog, which ->
                       dialog.dismiss()
                    }
                    .setPositiveButton(resources.getString(R.string.try_again)) { dialog, which ->
                        humorAdapter.refresh()
                        dialog.dismiss()
                    }
                    .show()
            }
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
            viewModel.listHumors
                .collect {
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
                getString(R.string.three_second),
                getString(R.string.five_second),
                getString(R.string.seven_second),
                getString(R.string.nine_second),
                getString(R.string.eleven_second)
            )
            val alertDialog = MaterialAlertDialogBuilder(mActivity)
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
                }
            if (humorAdapter.itemCount > 0) {
                alertDialog.show()
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}