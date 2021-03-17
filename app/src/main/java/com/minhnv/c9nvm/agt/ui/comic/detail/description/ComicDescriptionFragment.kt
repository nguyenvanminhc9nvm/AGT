package com.minhnv.c9nvm.agt.ui.comic.detail.description

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.ComicDescriptionFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.comic.adapter.DescriptionComicAdapter
import com.minhnv.c9nvm.agt.utils.AGTConstant

class ComicDescriptionFragment :
    BaseFragment<ComicDescriptionViewModel, ComicDescriptionFragmentBinding>() {
    private lateinit var descriptionAdapter: DescriptionComicAdapter
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
        val comicId = arguments?.getInt(AGTConstant.DESCRIPTION_ID)
        viewModel.getListComicDescription(comicId ?: 0)
        viewModel.listComicDescription.observe(viewLifecycleOwner) {
            descriptionAdapter.set(it)
        }
    }

}