package com.minhnv.c9nvm.agt.ui

import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ActivityMainBinding
import com.minhnv.c9nvm.agt.ui.base.ActivityController
import com.minhnv.c9nvm.agt.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), ActivityController {

    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun initView() {

    }

    override fun bindViewModel() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun switchFragment(fragmentId: Int) {
        navController.navigate(fragmentId)
    }


}