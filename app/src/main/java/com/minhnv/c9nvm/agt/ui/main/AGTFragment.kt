package com.minhnv.c9nvm.agt.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.AgtFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainPagerAdapter
import com.minhnv.c9nvm.agt.ui.base.BaseFragment

class AGTFragment : BaseFragment<AGTViewModel, AgtFragmentBinding>() {
    private lateinit var mainPagerAdapter: MainPagerAdapter
    override val sbindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AgtFragmentBinding
        get() = AgtFragmentBinding::inflate

    override fun createViewModel(): Class<AGTViewModel> {
        return AGTViewModel::class.java
    }

    override fun initView() {
        mainPagerAdapter = MainPagerAdapter(this)
        binding.vpAgtMain.adapter = mainPagerAdapter
        binding.vpAgtMain.offscreenPageLimit = 3
        binding.vpAgtMain.isUserInputEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.humor -> {
                    binding.vpAgtMain.currentItem = 0
                }
                R.id.comic -> {
                    binding.vpAgtMain.currentItem = 1
                }
                R.id.sport -> {
                    binding.vpAgtMain.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun bindViewModel() {

    }
}