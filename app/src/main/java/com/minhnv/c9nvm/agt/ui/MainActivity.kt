package com.minhnv.c9nvm.agt.ui

import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import com.minhnv.c9nvm.agt.R
import com.minhnv.c9nvm.agt.databinding.ActivityMainBinding
import com.minhnv.c9nvm.agt.ui.base.ActivityController
import com.minhnv.c9nvm.agt.ui.base.BaseActivity
import com.minhnv.c9nvm.agt.ui.base.BaseFragment
import com.minhnv.c9nvm.agt.ui.main.AGTFragment

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), ActivityController {

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        MobileAds.initialize(this){}
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, AGTFragment())
            .commit()
    }

    override fun bindViewModel() {

    }

    override fun switchFragment(fragmentId: BaseFragment<*, *>, bundle: Bundle?) {
        fragmentId.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragmentId.tag)
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.nav_host_fragment, fragmentId)
            .commit()
    }
}