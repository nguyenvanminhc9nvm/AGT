package com.minhnv.c9nvm.agt.ui.base

import android.os.Bundle

interface ActivityController {
    fun switchFragment(
        fragmentId: BaseFragment<*, *>,
        bundle: Bundle? = null,
        isAdd: Boolean = false
    )
}