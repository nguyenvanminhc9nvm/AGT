package com.minhnv.c9nvm.agt.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.AgtMainFragmentBinding
import com.minhnv.c9nvm.agt.ui.MainPagerAdapter
import com.minhnv.c9nvm.agt.ui.base.BaseFragment

class AGTMainFragment : BaseFragment<AGTMainViewModel, AgtMainFragmentBinding>() {
    private lateinit var mainPagerAdapter: MainPagerAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AgtMainFragmentBinding
        get() = AgtMainFragmentBinding::inflate

    override fun createViewModel(): Class<AGTMainViewModel> {
        return AGTMainViewModel::class.java
    }

    override fun initView() {
        mainPagerAdapter = MainPagerAdapter(this)
        binding.vpMain.adapter = mainPagerAdapter
        binding.vpMain.isUserInputEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    binding.vpMain.currentItem = 0
                }
                R.id.navigation_dashboard -> {
                    binding.vpMain.currentItem = 1
                }
                R.id.navigation_notifications -> {
                    binding.vpMain.currentItem = 2
                }
            }
            true
        }
    }

    override fun bindViewModel() {

    }


}