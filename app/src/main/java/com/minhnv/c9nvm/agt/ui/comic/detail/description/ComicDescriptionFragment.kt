package com.minhnv.c9nvm.agt.ui.comic.detail.description

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ComicDescriptionFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.DescriptionComicAdapter
import com.minhnv.c9nvm.agt.utils.AGTConstant
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ComicDescriptionFragment :
    BaseFragment<ComicDescriptionViewModel, ComicDescriptionFragmentBinding>() {
    private lateinit var descriptionAdapter: DescriptionComicAdapter
    private var page = 0
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDescriptionFragmentBinding
        get() = ComicDescriptionFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDescriptionViewModel> {
        return ComicDescriptionViewModel::class.java
    }

    override fun initView() {
        descriptionAdapter = DescriptionComicAdapter()
        binding.vpDescriptionComic.adapter = descriptionAdapter
    }

    override fun bindViewModel() {
        val array = arrayOf(
            "5 second",
            "10 second",
            "15 second",
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
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        viewModel.getListComicDescription(comicId ?: 0)
        viewModel.listComicDescription.observe(viewLifecycleOwner) {
            descriptionAdapter.set(it)
        }
        binding.vpDescriptionComic.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                page = position
            }
        })
    }

}