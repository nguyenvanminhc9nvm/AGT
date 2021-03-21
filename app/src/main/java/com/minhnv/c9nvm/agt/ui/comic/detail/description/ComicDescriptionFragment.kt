package com.minhnv.c9nvm.agt.ui.comic.detail.description

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicDescriptionFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.DescriptionComicAdapter
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ComicDescriptionFragment :
    BaseFragment<ComicDescriptionViewModel, ComicDescriptionFragmentBinding>() {
    private lateinit var descriptionAdapter: DescriptionComicAdapter
    private var page = 0
    private lateinit var mInterstitialAd: InterstitialAd
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDescriptionFragmentBinding
        get() = ComicDescriptionFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDescriptionViewModel> {
        return ComicDescriptionViewModel::class.java
    }

    override fun initView() {
        descriptionAdapter = DescriptionComicAdapter()
        binding.vpDescriptionComic.adapter = descriptionAdapter
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun bindViewModel() {
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        lifecycleScope.launch {
            viewModel.getPageWithComicId(comicId ?: 0).collect {
                binding.vpDescriptionComic.postDelayed({
                    binding.vpDescriptionComic.setCurrentItem(it, true)
                }, 200)
            }
        }
        val array = arrayOf(
            getString(R.string.seven_second),
            getString(R.string.eleven_second),
            getString(R.string.fifteen_second),
            getString(R.string.seventeen_second),
            getString(R.string.nineteen_second)
        )
        MaterialAlertDialogBuilder(mActivity)
            .setTitle(getString(R.string.select_time_skip))
            .setNeutralButton(getString(R.string.cancel)) { dig, w ->
                dig.dismiss()
            }
            .setSingleChoiceItems(array, 0) { dig, pos ->
                val time = array[pos].filter { it.isDigit() }.toLong()
                Observable.interval(time, TimeUnit.SECONDS)
                    .filter {
                        page <= descriptionAdapter.itemCount
                    }
                    .observeOn(schedulerProvider.ui)
                    .subscribe {
                        page++
                        binding.vpDescriptionComic.setCurrentItem(page, true)
                    }.addToDisposable()
                dig.dismiss()
            }.show()
        viewModel.getListComicDescription(comicId ?: 0)
        viewModel.listComicDescription.observe(viewLifecycleOwner) {
            descriptionAdapter.set(it)
            binding.vpDescriptionComic.offscreenPageLimit = descriptionAdapter.itemCount
        }
        binding.vpDescriptionComic.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                page = position
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        println("#comic save pos: $page")
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        viewModel.savePageWithComicId(comicId ?: 0, page)
    }
}