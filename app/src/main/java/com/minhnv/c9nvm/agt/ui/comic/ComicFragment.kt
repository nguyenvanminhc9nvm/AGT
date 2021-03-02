package com.minhnv.c9nvm.agt.ui.comic

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.ComicFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment

class ComicFragment : BaseFragment<ComicViewModel, ComicFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ComicFragmentBinding
        get() = ComicFragmentBinding::inflate

    override fun createViewModel(): Class<ComicViewModel> {
        return ComicViewModel::class.java
    }

    override fun initView() {

    }

    override fun bindViewModel() {

    }




}