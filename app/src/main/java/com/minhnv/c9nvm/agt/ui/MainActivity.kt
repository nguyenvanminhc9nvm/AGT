package com.minhnv.c9nvm.agt.ui

import android.view.LayoutInflater
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ActivityMainBinding
import com.minhnv.c9nvm.agt.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var mainPagerAdapter: MainPagerAdapter

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
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