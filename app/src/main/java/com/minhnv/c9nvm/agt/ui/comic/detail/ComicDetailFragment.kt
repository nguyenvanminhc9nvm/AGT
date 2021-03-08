package com.minhnv.c9nvm.agt.ui.comic.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.ComicDetailFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment

class ComicDetailFragment : BaseFragment<ComicDetailViewModel, ComicDetailFragmentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicDetailFragmentBinding
        get() = ComicDetailFragmentBinding::inflate

    override fun createViewModel(): Class<ComicDetailViewModel> {
        return ComicDetailViewModel::class.java
    }

    override fun initView() {

    }

    override fun bindViewModel() {

    }

}