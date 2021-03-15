package com.minhnv.c9nvm.agt.ui.sport

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.databinding.SportFragmentBinding
import com.minhnv.c9nvm.agt.ui.base.BaseFragment

class SportFragment : BaseFragment<SportViewModel, SportFragmentBinding>() {
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SportFragmentBinding
        get() = SportFragmentBinding::inflate

    override fun createViewModel(): Class<SportViewModel> {
        return SportViewModel::class.java
    }

    override fun initView() {
    }

    override fun bindViewModel() {
    }


}